package org.team10424102.blackserver.services;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.team10424102.blackserver.config.security.UserAuthentication;
import org.team10424102.blackserver.daos.UserRepo;
import org.team10424102.blackserver.exceptions.PersistEntityException;
import org.team10424102.blackserver.exceptions.UserNotFoundException;
import org.team10424102.blackserver.models.Gender;
import org.team10424102.blackserver.models.RegInfo;
import org.team10424102.blackserver.models.User;
import org.team10424102.blackserver.utils.DateUtils;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    public static final int HALF_AN_HOUR_IN_MILLISECONDS = 30 * 60 * 1000;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final Cache tokenCache = CacheManager.getInstance().getCache("userTokenCache");
    @Autowired UserRepo userRepo;
    @Autowired ImageService imageService;
    @Autowired EntityManager em;

    @Scheduled(fixedRate = HALF_AN_HOUR_IN_MILLISECONDS)
    public void evictExpiredTokens() {
        tokenCache.evictExpiredElements();
    }

    @NotNull
    @Override
    public String generateToken(User user) {
        UserAuthentication auth = new UserAuthentication(user);
        String token = UUID.randomUUID().toString();
        tokenCache.put(new Element(token, auth));
        return token;
    }

    @Nullable
    @Override
    public UserAuthentication getUserAuthenticationFromToken(String token) {
        Element element = tokenCache.get(token);
        if (element == null) return null;

        // 因为 Hibernate 的原因, 这里取出来的 User 是没有和数据库关联的, 或者里面的 SessionFactory 已经关闭
        // 暂且采用这个愚蠢的方法从数据库中再取一个新鲜的 User
        // TODO 缓存内的 User 保鲜
        UserAuthentication auth = (UserAuthentication)element.getObjectValue();
//        User user = (User)auth.getPrincipal();
//        User freshUser = userRepo.findOne(user.getId());
//        UserAuthentication freshAuth = new UserAuthentication(freshUser);
//        return freshAuth;
        return auth;
    }

    @Override
    public boolean isTokenValid(String token) {
        Element element = tokenCache.get(token);
        return element != null;
    }

    @NotNull
    @Override
    public User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // TODO 搭配使用 Ehcache 和 Hibernate
        return userRepo.findOne(user.getId());
    }

    @Override
    public long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    @NotNull
    @Override
    public User createAndSaveUser(String phone, HttpServletRequest request) {
        User user = new User();
        user.setUsername(phone);
        user.setEnabled(true);

        user.setPhone(phone);
        user.setGender(Gender.UNKNOWN);
        user.setAvatar(imageService.getDefault().avatar());
        user.setBackground(imageService.getDefault().background());

        RegInfo r = user.getRegInfo();
        r.setRegIp(request.getRemoteAddr());
        r.setRegTime(DateUtils.now());

        User savedUser = userRepo.save(user);
        if (savedUser == null) throw new PersistEntityException(User.class);
        return savedUser;
    }

    @Override
    public void deleteCurrentUser() {
        userRepo.delete(getCurrentUserId());
    }

    @NotNull
    @Override
    public User getUserById(long id) {
        User user = userRepo.findOne(id);
        if (user == null) throw new UserNotFoundException(id);
        return user;
    }

    @Override
    public boolean isPhoneExisted(String phone) {
        return userRepo.existsByPhone(phone);
    }

    @Override
    public User findByPhone(String phone) {
        return userRepo.findByPhone(phone);
    }

    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }
}
