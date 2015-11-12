package com.example.controllers;

import com.example.Api;
import com.example.config.jsonviews.UserView;
import com.example.daos.AcademyRepo;
import com.example.daos.CollegeRepo;
import com.example.daos.UserRepo;
import com.example.models.*;
import com.example.services.ImageService;
import com.example.services.TokenService;
import com.example.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.example.Api.SUCCESS;
import static com.example.Api.UPDATE_TOKEN_FAILED;

@RestController
public class UserController {
    static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepo userRepo;

    @Autowired
    CollegeRepo collegeRepo;

    @Autowired
    AcademyRepo academyRepo;

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    ImageService imageService;

    /**
     * 更新用户无效的 token
     *
     * @param phone
     * @return
     */
    @RequestMapping(value = "/api/token", method = RequestMethod.GET)
    public Api.Result getToken(@RequestParam String phone) {
        User user = userRepo.findOneByPhone(phone);
        if (user == null) return Api.result(UPDATE_TOKEN_FAILED);
        return Api.result(SUCCESS).param("token").value(tokenService.generateToken(user));
    }

    /**
     * 获取当前用户的个人信息
     *
     * @return 用户的个人信息
     */
    @RequestMapping(value = "/api/profile", method = RequestMethod.GET)
    @JsonView(UserView.Profile.class)
    public ResponseEntity<?> getMyProfile() {
        User user = userService.getCurrentUser();
        if (user != null) {
            Profile profile = user.getProfile();
            if (profile == null) {
                logger.warn("用户" + user.getId() + "的[Profile]为Null。");
            } else {
                Image avatar = profile.getAvatar();
                if (avatar != null) {
                    profile.setAvatarAccessToken(imageService.generateAccessToken(avatar));
                }
                Image background = profile.getBackgroundImage();
                if (background != null) {
                    profile.setBackgroundImageAccessToken(imageService.generateAccessToken(background));
                }
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 更新用户的个人信息
     *
     * @param updatedUser 新的用户个人信息，不含头像
     * @return HTTP状态码NO_CONTENT表示操作成功，BAD_REQUEST表示参数异常，未登录则返回UNAUTHORIZED
     */
    @RequestMapping(value = "/api/profile", method = RequestMethod.PUT)
    @JsonView(UserView.Profile.class)
    public ResponseEntity<?> setMyProfile(@RequestBody User updatedUser) {
        //TODO 进行数据验证
        User user = userService.getCurrentUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        updatedUser.setId(user.getId());
        updatedUser.setPhone(user.getPhone());
        updatedUser.setEnabled(updatedUser.isEnabled());
        updatedUser.setRegInfo(user.getRegInfo());
        College updatedCollege = updatedUser.getProfile().getCollege();
        College college = collegeRepo.findOneByName(updatedCollege.getName());
        Academy updatedAcademy = updatedUser.getProfile().getAcademy();
        Academy academy = academyRepo.findOneByName(updatedAcademy.getName());
        if (college != null && academy != null) {
            updatedUser.getProfile().setCollege(college);
            updatedUser.getProfile().setAcademy(academy);
            userRepo.save(updatedUser);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/api/reginfo", method = RequestMethod.GET)
    public ResponseEntity<?> getMyRegInfo() {
        User user = userService.getCurrentUser();
        if (user != null) {
            RegistrationInfo registrationInfo = user.getRegInfo();
            return new ResponseEntity<>(registrationInfo, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/api/users/{userId}/profile", method = RequestMethod.GET)
    @JsonView(UserView.Profile.class)
    public ResponseEntity<?> getUserProfile(@PathVariable int userId) {
        User user = userRepo.findOne(userId);
        if (user != null) {
            Profile profile = user.getProfile();
            if (profile == null) {
                logger.warn("用户" + user.getId() + "的[Profile]为Null。");
            } else {
                Image avatar = profile.getAvatar();
                if (avatar != null) {
                    profile.setAvatarAccessToken(imageService.generateAccessToken(avatar));
                }
                Image background = profile.getBackgroundImage();
                if (background != null) {
                    profile.setBackgroundImageAccessToken(imageService.generateAccessToken(background));
                }
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/api/profile/friends", method = RequestMethod.GET)
    @JsonView(UserView.UserSummary.class)
    public ResponseEntity<?> getMyFriendList() {
        final PageRequest pageable = new PageRequest(0, 10, Sort.Direction.ASC, "id");
        //TODO 将查询朋友的功能真正实现
        Page<User> users = userRepo.findAll(pageable);
        if (users.getTotalElements() > 0) {
            List<User> userList = users.getContent();
            Iterator<User> iterator = userList.iterator();
            User user = null;
            Image avatar = null;
            Image background = null;
            Profile profile = null;
            while (iterator.hasNext()) {
                user = iterator.next();
                if (user != null) {
                    profile = user.getProfile();
                    if (profile == null) {
                        logger.warn("用户" + user.getId() + "的[Profile]为Null。");
                    } else {
                        if (avatar != null) {
                            profile.setAvatarAccessToken(imageService.generateAccessToken(avatar));
                        }
                        if (background != null) {
                            profile.setBackgroundImageAccessToken(imageService.generateAccessToken(background));
                        }
                    }
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 检查目标用户是否已经被关注了
     *
     * @param id 目标id
     * @return 已经关注过则返回NO_CONTENT，未关注则返回NOT_FOUND，未登录则返回UNAUTHORIZED
     */
    @RequestMapping(value = "/api/following/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> checkHasFollowed(@PathVariable Integer id) {
        if (id != null) {
            User currentUser = userService.getCurrentUser();
            if (currentUser == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Set<User> following = currentUser.getFollowing();
            for (Iterator<User> iterator = following.iterator(); iterator.hasNext(); ) {
                User checkUser = iterator.next();
                if (checkUser.getId().equals(id)) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * 获取当前用户的关注列表
     *
     * @return 如果未登录则返回UNAUTHORIZED，没有则返回NOT_FOUND，否则返回关注用户基本信息列表
     */
    @RequestMapping(value = "/api/following", method = RequestMethod.GET)
    @JsonView(UserView.UserSummary.class)
    public ResponseEntity<?> getFollowList() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Set<User> following = currentUser.getFollowing();
        if (following.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for (Iterator<User> iterator = following.iterator(); iterator.hasNext(); ) {
            User checkUser = iterator.next();
            Profile profile = checkUser.getProfile();
            if (profile != null) {
                if (profile.getAvatar() != null) {
                    profile.setAvatarAccessToken(imageService.generateAccessToken(profile.getAvatar()));
                }
                if (profile.getBackgroundImage() != null) {
                    profile.setBackgroundImageAccessToken(imageService.generateAccessToken(profile.getBackgroundImage()));
                }
            }
        }
        return new ResponseEntity<>(following, HttpStatus.BAD_REQUEST);
    }

    /**
     * 关注一个用户
     *
     * @param id 要关注的对象的id
     * @return 操作成功返回NO_CONTENT，参数错误返回BAD_REQUEST，未登陆则返回UNAUTHORIZED
     */
    @RequestMapping(value = "/api/following/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> doFollow(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.getCurrentUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (user.getId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User followedUser = userRepo.findOne(id);
        if (followedUser != null) {
            Set<User> following = user.getFollowing();
            boolean hasFollowed = false;
            for (Iterator<User> iterator = following.iterator(); iterator.hasNext(); ) {
                User checkUser = iterator.next();
                if (checkUser.getId().equals(id)) {
                    hasFollowed = true;
                    break;
                }
            }
            if (!hasFollowed) {
                following.add(followedUser);
                userRepo.save(user);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * 取消关注一个用户
     *
     * @param id 要取消关注的用户的id
     * @return 操作成功返回NO_CONTENT，参数错误返回BAD_REQUEST，未登陆则返回UNAUTHORIZED
     */
    @RequestMapping(value = "/api/following/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> unFollow(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.getCurrentUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User followedUser = userRepo.findOne(id);
        if (followedUser != null) {
            Set<User> following = user.getFollowing();
            for (Iterator<User> iterator = following.iterator(); iterator.hasNext(); ) {
                User checkUser = iterator.next();
                if (checkUser.getId().equals(id)) {
                    iterator.remove();
                    break;
                }
            }
            userRepo.save(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * 获取关注当前用户的用户列表
     *
     * @return 未登录则返回UNAUTHORIZED，如果列表为空则返回NOT_FOUND，否则返回用户列表
     */
    @RequestMapping(value = "/api/followed", method = RequestMethod.GET)
    @JsonView(UserView.UserSummary.class)
    public ResponseEntity<?> getFollowedList() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Set<User> followed = currentUser.getFollowed();
        if (followed.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for (Iterator<User> iterator = followed.iterator(); iterator.hasNext(); ) {
            User checkUser = iterator.next();
            Profile profile = checkUser.getProfile();
            if (profile != null) {
                if (profile.getAvatar() != null) {
                    profile.setAvatarAccessToken(imageService.generateAccessToken(profile.getAvatar()));
                }
                if (profile.getBackgroundImage() != null) {
                    profile.setBackgroundImageAccessToken(imageService.generateAccessToken(profile.getBackgroundImage()));
                }
            }
        }
        return new ResponseEntity<>(followed, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/api/friend/num", method = RequestMethod.GET)
    @JsonView(UserView.Profile.class)
    public ResponseEntity<?> getFriendNumber() {
        Api.Result result = Api.result(SUCCESS).param("num").value(123);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/followed/num", method = RequestMethod.GET)
    public ResponseEntity<?> getFollowNumber() {
        Api.Result result = Api.result(SUCCESS).param("num").value(200);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/fan/num", method = RequestMethod.GET)
    public ResponseEntity<?> getFanNumber() {
        Api.Result result = Api.result(SUCCESS).param("num").value(456);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/likes/num", method = RequestMethod.GET)
    public ResponseEntity<?> getLikeNumber() {
        Api.Result result = Api.result(SUCCESS).param("num").value(789);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(HttpMessageNotReadableException e) {
        logger.warn("Returning HTTP 400 Bad Request", e);
    }

}
