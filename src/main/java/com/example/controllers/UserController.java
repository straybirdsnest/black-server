package com.example.controllers;

import com.example.config.jsonviews.UserView;
import com.example.daos.FriendshipRepo;
import com.example.exceptions.*;
import com.example.models.Friendship;
import com.example.models.User;
import com.example.services.UserService;
import com.example.services.VcodeService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;

import static com.example.App.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class UserController {

    @Autowired UserService userService;

    @Autowired FriendshipRepo friendshipRepo;

    @Autowired VcodeService vcodeService;

//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        binder.registerCustomEditor(User.class, new UserTypeEditor());
//    }

    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                          USER                               //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////

    //<editor-fold desc="=== Account ===">

    /**
     * 更新用户无效的 token，需要先向 Mob 验证手机，然后再给用户刷新 token
     */
    @RequestMapping(value = API_TOKEN, method = GET)
    public String getToken(@RequestParam String phone, @RequestParam String vcode) {
//        User user = userService.getCurrentUser();
//        if (!phone.equals(user.getProfile().getPhone()))
//            throw new UpdateTokenWithOtherPhoneException(user.getId(), phone);
        if (!vcodeService.verify("86", phone, vcode))
            throw new VcodeVerificationException(phone, vcode);
        return userService.generateTokenByPhone(phone);
    }

    /**
     * 检查可用性
     * type = phone|token
     */
    @RequestMapping(value = API_AVAILABILITY + "/{type}", method = GET)
    public boolean isAvailable(@PathVariable String type, @RequestParam(value = "q") String content) {
        switch (type) {
            case "phone":
                return !userService.isPhoneExisted(content);
            case "token":
                return userService.isTokenValid(content);
        }
        return false;
    }

    /**
     * 注册新用户，通过向 Mob 发送手机验证码，来验证用户的身份，从而注册新用户
     *
     * @return 如果注册成功则返回访问 API 的 token
     */
    @RequestMapping(value = API_USER, method = POST)
    public String register(@RequestParam String phone, @RequestParam String vcode, HttpServletRequest request) {
        if (!vcodeService.verify("86", phone, vcode)) throw new RegistedWithInvalidVcodeException(vcode);
        boolean existed = userService.isPhoneExisted(phone);
        if (existed) throw new RegistedWithExistedPhoneException(phone);
        User user = userService.createAndSaveUser(phone, request);
        return userService.generateToken(user);
    }

    /**
     * 注销当前用户
     */
    @RequestMapping(value = API_USER, method = DELETE)
    public void unregister() {
        userService.deleteCurrentUser();
    }

    /**
     * 获取当前用户的个人信息
     */
    @RequestMapping(value = API_USER, method = GET)
    @JsonView(UserView.Profile.class)
    public User getCurrentUsersProfile() {
        return userService.getCurrentUser();
    }

    /**
     * 更新当前用户的个人信息
     */
    @RequestMapping(value = API_USER, method = PUT)
    public void updateCurrentUsersProfile(@JsonView(UserView.Profile.class) User newProfile) {
        userService.updateUser(newProfile);
    }

    /**
     * 查看其他用户的 Profile
     */
    @RequestMapping(value = API_USER + "/{id}", method = GET)
    @JsonView(UserView.Profile.class)
    public User getOthersProfile(@PathVariable int id) {
        return userService.getUserById(id);
    }

    //</editor-fold>

    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                       SUBCRIPTION                           //
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////

    //<editor-fold desc="=== Subscription ===">

    /**
     * 获取当前用户的关注列表
     */
    @RequestMapping(value = API_FOCUSES, method = GET)
    @JsonView(UserView.UserSummary.class)
    public Set<User> getCurrentUsersFocuses() {
        return userService.getCurrentUser().getFocuses();
    }

    /**
     * 获取当前用户的粉丝列表
     */
    @RequestMapping(value = API_FANS, method = GET)
    @JsonView(UserView.UserSummary.class)
    public Set<User> getCurrentUsersFans() {
        return userService.getCurrentUser().getFocuses();
    }

    /**
     * 关注一个用户
     */
    @RequestMapping(value = API_FOCUSES + "/{id}", method = POST)
    public void focusSomeone(@PathVariable int id) {
        User user = userService.getCurrentUser();
        // 不能关注自己
        if (user.getId() == id) throw new FocusMyselfException(id);
        User focus = userService.getUserById(id);
        user.getFocuses().add(focus);
        userService.updateUser(user);
        // 关注另一个人的同时你也成为了他的粉丝
        // TODO 检查实体 cascade 是否成立
    }

    /**
     * 取消关注一个用户
     */
    @RequestMapping(value = API_FOCUSES + "/{id}", method = DELETE)
    public void unfocusSomeone(@PathVariable int id) {
        User user = userService.getCurrentUser();
        user.getFocuses().removeIf(u -> u.getId() == id);
        // 取消关注一个人的同时你也会从他的粉丝列表里消失
        // TODO 检查实体 cascade 是否成立
    }

    //</editor-fold>

    /////////////////////////////////////////////////////////////////
    //                                                             //
    //                    ~~~~~~~~~~~~~~~~~                        //
    //                        FRIENDSHIP                           // 
    //                    =================                        //
    //                                                             //
    /////////////////////////////////////////////////////////////////

    //<editor-fold desc="=== Friendship ===">

    /**
     * 获取当前用户的朋友
     */
    @RequestMapping(value = API_FRIENDS, method = GET)
    @JsonView(UserView.UserSummary.class)
    public Set<Friendship> getCurrentUsersFriends() {
        return userService.getCurrentUser().getFriendshipSet();
    }

    /**
     * 添加一个朋友
     */
    @RequestMapping(value = API_FRIENDS + "/{id}", method = POST)
    public void friendSomeone(@PathVariable int id, @RequestParam(required = false) String alias) {
        User user = userService.getCurrentUser();
        // 不能和自己成为朋友
        if (user.getId() == id) throw new FriendMyselfException(id);
        User friend = userService.getUserById(id);

        Friendship friendship = new Friendship();
        friendship.setFriend(friend);
        friendship.setFriendAlias(alias);
        friendship.setUser(user);
        friendshipRepo.save(friendship);

        // 保存这个朋友关系的同时, 双方的朋友列表里都会有对方
        // TODO 检查实体 cascade 是否成立
    }

    /**
     * 删除一个朋友
     */
    @RequestMapping(value = API_FRIENDS + "/{id}", method = DELETE)
    public void unfriendSomeone(@PathVariable int id) {
        User user = userService.getCurrentUser();
        user.getFriendshipSet().removeIf(f -> f.getUser().getId() == id);

        // 删除这个朋友关系的同时, 双方的朋友列表里都会删除对方
        // TODO 检查实体 cascade 是否成立
    }

    /**
     * 修改一个朋友的备注名称
     */
    @RequestMapping(value = API_FRIENDS + "/{id}", method = PUT)
    public void updateFriendAlias(@PathVariable int id, @RequestParam String alias) {
        User user = userService.getCurrentUser();
        Optional<Friendship> friendship = user.getFriendshipSet().stream()
                .filter(f -> f.getFriend().getId() == id).findFirst();
        if (friendship.isPresent()) {
            Friendship f = friendship.get();
            f.setFriendAlias(alias);
            friendshipRepo.save(f);
        }
    }

    //</editor-fold>

}
