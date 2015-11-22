package com.example.services.impl;

import com.example.daos.UserRepo;
import com.example.exceptions.PersistEntityException;
import com.example.exceptions.UserNotFoundException;
import com.example.models.Gender;
import com.example.models.Profile;
import com.example.models.RegInfo;
import com.example.models.User;
import com.example.services.CurrentThreadUserService;
import com.example.services.ImageService;
import com.example.services.UserService;
import com.example.utils.DateUtils;
import com.example.utils.EntityUpdateHelper;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired UserRepo userRepo;

    @Autowired ImageService imageService;

    @Autowired CurrentThreadUserService currentThreadUserService;

    @Autowired EntityManager em;

    @NotNull
    @Override
    public User getCurrentUser() {
        int id = currentThreadUserService.getCurrentThreadUserId();
        logger.debug("获取用户 " + id);
        Session session = em.unwrap(Session.class);
        User user = (User) session.load(User.class, id);
        if (user == null) {
            String errorMsg = String.format("无法在数据库内找到用户 (id = %d)", id);
            RuntimeException e = new RuntimeException(errorMsg);
            logger.error(errorMsg, e);
            throw e;
        }
        return user;
    }

    @Override
    public int getCurrentUserId() {
        //return currentThreadUserService.getCurrentThreadUserId();
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

    @Override
    public boolean isSecondsFriend(int firstUid, int secondUid) {
        return userRepo.friendOf(firstUid, secondUid);
    }

    @Override
    public boolean isSecondsFan(int firstUid, int secondUid) {
        return userRepo.fanOf(firstUid, secondUid);
    }

    @Override
    public boolean isSecondsFocus(int firstUid, int secondUid) {
        return userRepo.focusOf(firstUid, secondUid);
    }

    @NotNull
    @Override
    public User createAndSaveUser(String phone, HttpServletRequest request) {
        User user = new User();
        user.setUsername(phone);
        user.setEnabled(true);

        Profile p = user.getProfile();
        p.setPhone(phone);
        p.setGender(Gender.SECRET);
        p.setAvatar(imageService.getDefault().avatar());
        p.setBackgroundImage(imageService.getDefault().background());

        RegInfo r = user.getRegInfo();
        r.setRegIp(request.getRemoteAddr());
        r.setRegTime(DateUtils.now());

        User savedUser = userRepo.save(user);
        if (savedUser == null) throw new PersistEntityException(User.class);
        return savedUser;
    }

    @NotNull
    @Override
    public User updateUser(User u) {
        // 首先传入进来的 user 是数据格式合法的 user, 这里检测更新的逻辑合法性
        u = filterUnchangableFields(u);
        User existedUser = getCurrentUser();
        EntityUpdateHelper.copyNonNullProperties(existedUser, u);
        User updatedUser = userRepo.save(u);
        if (updatedUser == null) throw new PersistEntityException(User.class);
        return updatedUser;
    }

    private User filterUnchangableFields (User u) {
        // id 虽然不可改变, 但是 hibernate 保存的时候设置的 id 是无效的, 所以这里可以不用过滤
        u.setRegInfo(null);
        // TODO 用户设置邮箱需要验证邮箱真伪, 所以这里暂且过滤
        u.setEmail(null);
        u.setEnabled(null);
        u.setFocuses(null);
        u.setFans(null);
        u.setFriendshipSet(null);
        u.setMembershipSet(null);

        Profile p = u.getProfile();
        // TODO 更改手机号码需要再次短信验证, 这里暂且过滤
        p.setPhone(null);
        // TODO 更改 idcard 和 realname 需要实名验证系统的完善, 这里暂且过滤
        p.setRealName(null);
        p.setIdCard(null);
        // 头像和背景在 jackson 转换的时候已经获取, 非法的token会导致这里的头像和背景是 null, 不影响更新
        // 大学和学院也是在 jackson 转换的时候确认的合法性

        return u;
    }

    @Override
    public void deleteCurrentUser() {
        int id = getCurrentUserId();
        userRepo.delete(id);
    }

    @NotNull
    @Override
    public User getUserById(int id) {
        User user = userRepo.findOne(id);
        if (user == null) throw new UserNotFoundException(id);
        return user;
    }

    @Override
    public boolean isPhoneExisted(String phone) {
        return userRepo.existsByPhone(phone);
    }
}
