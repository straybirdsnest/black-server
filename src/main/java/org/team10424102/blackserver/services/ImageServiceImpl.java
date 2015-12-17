package org.team10424102.blackserver.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.team10424102.blackserver.App;
import org.team10424102.blackserver.models.ImageRepo;
import org.team10424102.blackserver.models.Image;

@Service
public class ImageServiceImpl implements ImageService, DefaultImage {
    @Autowired ImageRepo imageRepo;
    @Autowired ApplicationContext context;

    private Image avatar;
    private final Object avatarLock = new Object();
    private Image background;
    private final Object backgroundLock = new Object();
    private Image cover;
    private final Object coverLock = new Object();


    /**
     * 获得系统默认图片
     */
    @NotNull
    public DefaultImage getDefault() {
        return this;
    }

    @Override
    public Image avatar() {
        if (avatar != null) return avatar;
        synchronized (avatarLock) {
            if (avatar != null) return avatar;

            avatar = imageRepo.findOneByTags(App.DEFAULT_AVATAR_TAG);
            if (avatar == null) {
                throw new IllegalStateException("缺少图片: 用户默认头像");
            }
        }
        return avatar;
    }

    @Override
    public Image background() {
        if (background != null) return background;
        synchronized (backgroundLock) {
            if (background != null) return background;

            background = imageRepo.findOneByTags(App.DEFAULT_BACKGROUND_TAG);
            if (background == null) {
                throw new IllegalStateException("缺少图片: 用户默认背景");
            }
        }
        return background;
    }

    @Override
    public Image cover() {
        if (cover != null) return cover;
        synchronized (coverLock) {
            if (cover != null) return cover;

            cover = imageRepo.findOneByTags(App.DEFAULT_COVER_TAG);
            if (cover == null) {
                throw new IllegalStateException("缺少图片: 活动默认封面");
            }
        }
        return cover;
    }
}
