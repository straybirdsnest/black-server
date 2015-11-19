package com.example.services.impl;

import com.example.App;
import com.example.config.security.ImageToken;
import com.example.daos.ImageAccessPermissionRepo;
import com.example.daos.ImageRepo;
import com.example.exceptions.IllegalTokenException;
import com.example.exceptions.PersistEntityException;
import com.example.exceptions.SystemError;
import com.example.models.Image;
import com.example.models.ImageAccessPermission;
import com.example.models.User;
import com.example.models.UserGroup;
import com.example.services.DefaultImage;
import com.example.services.GroupService;
import com.example.services.ImageService;
import com.example.services.UserService;
import com.example.utils.Cryptor;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Date;
import java.util.List;

import static com.example.models.ImageAccessPermission.FLAG_PRIVATE;
import static com.example.models.ImageAccessPermission.FLAG_USER;

@Service
public class ImageServiceImpl implements ImageService, DefaultImage {
    static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
    @Autowired ImageRepo imageRepo;
    @Autowired ImageAccessPermissionRepo imageAccessPermissionRepo;
    @Autowired UserService userService;
    @Autowired GroupService groupService;
    @Autowired ApplicationContext context;
    private Image avatar;
    private Image background;
    private Image cover;

    @Autowired
    public ImageServiceImpl(ApplicationContext context, ImageRepo imageRepo,
                            ImageAccessPermissionRepo imageAccessPermissionRepo) throws IOException{
        this.imageRepo = imageRepo;
        this.imageAccessPermissionRepo = imageAccessPermissionRepo;

        // TODO 检查路径在 Windows 上是否起作用
        avatar = imageRepo.findOneByTags(App.DEFAULT_AVATAR_TAG);
        if (avatar == null) {
            byte[] data = IOUtils.toByteArray(
                    context.getResource("classpath:default/default_avatar.png").getInputStream());
            avatar = saveDefaultImage(data, App.DEFAULT_AVATAR_TAG);
        }

        background = imageRepo.findOneByTags(App.DEFAULT_BACKGROUND_TAG);
        if (background == null) {
            byte[] data = IOUtils.toByteArray(
                    context.getResource("classpath:default/default_background.png").getInputStream());
            background = saveDefaultImage(data, App.DEFAULT_BACKGROUND_TAG);
        }

        cover = imageRepo.findOneByTags(App.DEFAULT_COVER_TAG);
        if (cover == null) {
            byte[] data = IOUtils.toByteArray(
                    context.getResource("classpath:default/default_cover.png").getInputStream());
            cover = saveDefaultImage(data, App.DEFAULT_COVER_TAG);
        }
    }

    /**
     * 为当前用户生成一张图片的访问 token
     */
    @NotNull
    public String generateAccessToken(Image image) {
        int uid = userService.getCurrentUserId();
        List<ImageAccessPermission> permissions = imageAccessPermissionRepo.findByImage(image);
        ImageAccessPermission permission = null;
        for (ImageAccessPermission p : permissions) {
            if (canAccessImage(uid, p)) {
                permission = p;
                break;
            }
        }
        if (permission == null) {
            // 在图片的所有访问权限中找不到满足当前用户的权限
            // 这种情况在系统正常运行的情况下是不会发生的
            throw new SystemError(
                    String.format("系统中保存这一张用户 (id = %d) 无权访问的图片 (id = %d)", uid, image.getId()), null);
        }
        return encrypt(image, permission);
    }

    /**
     * token 的组成
     * - flags  int32
     * - uid    int32
     * - gid    int64
     * - expire int64
     * - id     int64
     * 总共 40 个字节
     */
    private String encrypt(Image image, ImageAccessPermission permission) {
        ImageToken token = new ImageToken();
        token.setFlags(permission.getFlags());
        token.setUid(permission.getUid());
        token.setGid(permission.getGid());
        token.setGid(new Date().getTime() + ImageToken.EXPIRE);
        token.setId(image.getId());

        try (ByteArrayOutputStream arrOut = new ByteArrayOutputStream(ImageToken.SIZE);
             ObjectOutputStream out = new ObjectOutputStream(arrOut)
        ) {
            out.writeObject(token);
            return Cryptor.encrypt(arrOut.toByteArray()) + "~" + image.getHash();
        } catch (IOException e) {
            logger.error("无法序列化 ImageToken", e);
            throw new SystemError("无法序列化 ImageToken", e);
        }
    }

    /**
     * 通过图片的访问 token, 为当前用户获得图片的真实 id
     * 如果当前用户无权限来访问这一张图片则返回 null
     */
    @Nullable
    public Long getImageIdFromAccessToken(String accessToken) {
        try {
            int uid = userService.getCurrentUserId();
            ImageWithPermission imageP = decrypt(accessToken);
            if (canAccessImage(uid, imageP.permission)) return imageP.image.getId();
        } catch (IllegalTokenException e) {
            logger.debug("非法的图片访问 token", e);
            return null;
        }
        return null;
    }

    private ImageWithPermission decrypt(String accessToken) throws IllegalTokenException {
        String[] parts = accessToken.split("~");
        if (parts.length < 2) throw new IllegalTokenException();

        byte[] data = Cryptor.decrypt(parts[0]);
        if (data == null) throw new IllegalTokenException();
        try (ByteArrayInputStream arrIn = new ByteArrayInputStream(data);
             ObjectInputStream in = new ObjectInputStream(arrIn)
        ) {
            ImageToken token = (ImageToken) in.readObject();
            
            // token 过期
            if (token.getExpire() < new Date().getTime()) throw new IllegalTokenException();

            Image image = new Image();
            image.setId(token.getId());

            ImageAccessPermission permission = new ImageAccessPermission();
            permission.setFlags(token.getFlags());
            permission.setUid(token.getUid());
            permission.setGid(token.getGid());

            return new ImageWithPermission(image, permission);

        } catch (IOException e) {
            logger.error("无法反序列化 ImageToken", e);
            throw new SystemError("无法反序列化 ImageToken", e);
        } catch (ClassNotFoundException e) {
            throw new IllegalTokenException();
        }
    }

    /**
     * 判断 uid 用户是否有足够的权限来访问这张图片
     */
    private boolean canAccessImage(int uid, ImageAccessPermission permission) {
        int flags = permission.getFlags();
        if ((flags & ImageAccessPermission.FLAG_PRIVATE) == 0) return true; // 公共图片
        if ((flags & ImageAccessPermission.FLAG_USER) > 0) {
            if (uid == permission.getUid()) return true; // 就是用户自己的图片
        }
        if ((flags & ImageAccessPermission.FLAG_USER_GROUP) > 0) {
            long gid = permission.getGid();
            if (gid >= UserGroup.GID_BASE) {
                // 检查当前用户是否为该组的成员
                if (groupService.isUserInGroup(uid, gid)) return true;
            } else {
                int gidInt = (int) gid;
                int imageUid = permission.getUid();
                switch (gidInt) {
                    case UserGroup.GID_FRIENDS:
                        // 检查当前用户是否是图片 uid 的朋友
                        if (userService.isSecondsFriend(uid, imageUid)) return true;
                        break;
                    case UserGroup.GID_FOLLOWINGS:
                        // 检查当前用户是否是图片 uid 的粉丝
                        if (userService.isSecondsFan(uid, imageUid)) return true;
                        break;
                    case UserGroup.GID_FANS:
                        // 检查当前用户是否是图片 uid 关注的人
                        if (userService.isSecondsFocus(uid, imageUid)) return true;
                        break;
                }
            }
        }
        return false;
    }

    /**
     * 获得系统默认图片
     */
    @NotNull
    public DefaultImage getDefault() {
        return this;
    }

    /**
     * 为当前用户保存一张图片, 并保存默认的访问权限
     * (即只有当前用户才能访问的私有图片)
     * 当 API 再次将图片和其他实体(例如活动)关联的时候, 会修改相应的访问权限
     * 例如: 允许组内成员访问的图片
     */
    @NotNull
    public Image saveImageWithDefaultPermission(byte[] data, String tags) {
        data = convertToPNGImageData(data);
        String hash = hash(data);
        Image image = imageRepo.findOneByHash(hash);
        Image savedImage = null;
        if (image == null) {
            image = new Image();
            image.setData(data);
            image.setHash(hash);
            image.setUsed(0);
            image.setTags(tags);
            savedImage = imageRepo.save(image);
        }

        ImageAccessPermission permission = new ImageAccessPermission();
        permission.setFlags(FLAG_PRIVATE | FLAG_USER);
        permission.setUid(userService.getCurrentUserId());
        permission.setGid((long) UserGroup.GID_EMPTY);
        permission.setImage(image);

        ImageAccessPermission savedPermission = imageAccessPermissionRepo.save(permission);

        if (savedImage == null) throw new PersistEntityException(Image.class);
        if (savedPermission == null) throw new PersistEntityException(ImageAccessPermission.class);

        return savedImage;
    }

    private Image saveDefaultImage(byte[] data, String tags) {
        data = convertToPNGImageData(data);
        String hash = hash(data);
        Image savedImage = null;
        Image image;
        image = new Image();
        image.setData(data);
        image.setHash(hash);
        image.setUsed(0);
        image.setTags(tags);
        savedImage = imageRepo.save(image);

        ImageAccessPermission permission = new ImageAccessPermission();
        permission.setFlags(0); // 公共图片
        permission.setUid(User.UID_PUBLIC);
        permission.setGid((long) UserGroup.GID_EMPTY);
        permission.setImage(image);

        ImageAccessPermission savedPermission = imageAccessPermissionRepo.save(permission);

        if (savedImage == null) throw new PersistEntityException(Image.class);
        if (savedPermission == null) throw new PersistEntityException(ImageAccessPermission.class);

        return savedImage;
    }

    /**
     * 转换用户提交的图片数据
     */
    private byte[] convertToPNGImageData(byte[] bytes) {
        // TODO convert to png image data
        return bytes;
    }

    private String hash(byte[] data) {
        return Cryptor.md5(data);
    }

    @Override
    public Image avatar() {
        return avatar;
    }

    @Override
    public Image background() {
        return background;
    }

    @Override
    public Image cover() {
        return cover;
    }

    private static class ImageWithPermission {
        public Image image;
        public ImageAccessPermission permission;

        public ImageWithPermission(Image image, ImageAccessPermission permission) {
            this.image = image;
            this.permission = permission;
        }
    }
}
