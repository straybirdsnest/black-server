package com.example.controllers;

import com.example.Api;
import com.example.config.jsonviews.UserView;
import com.example.daos.UserRepo;
import com.example.models.Profile;
import com.example.models.RegistrationInfo;
import com.example.models.User;
import com.example.services.TokenService;
import com.example.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.example.Api.SUCCESS;
import static com.example.Api.UPDATE_TOKEN_FAILED;

@RestController
public class UserController {
    static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

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

    @RequestMapping(value = "/api/profile/avatar", method = RequestMethod.GET)
    public ResponseEntity<?> getAvatar() {
        User user = userService.getCurrentUser();
        if (user != null && user.getProfile() != null && user.getProfile().getAvatar() != null) {
            byte[] avatar = user.getProfile().getAvatar();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(avatar.length);
            return new ResponseEntity<byte[]>(avatar, headers, HttpStatus.OK);
        }
        //TODO 使用非固定URI读取资源
        Resource resource = applicationContext.getResource("url:http://localhost:8080/images/defaultavatar.png");
        try {
            InputStream inputStream = resource.getInputStream();
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "PNG", byteArrayOutputStream);
            byte[] avatar = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(avatar.length);
            return new ResponseEntity<byte[]>(avatar, headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/api/profile/avatar", method = RequestMethod.POST)
    public ResponseEntity<?> setAvatar(@RequestParam MultipartFile file) {
        //TODO 扩展默认使用ImageIO导致的某些格式无法转换
        User user = userService.getCurrentUser();
        if (user != null && !file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                InputStream inputStream = new ByteArrayInputStream(bytes);
                BufferedImage bufferedImage = ImageIO.read(inputStream);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "PNG", byteArrayOutputStream);
                byte[] avatar = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                user.getProfile().setAvatar(avatar);
                userRepo.save(user);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @JsonView(UserView.ProfileWithoutAvatar.class)
    @RequestMapping(value = "/api/profile", method = RequestMethod.GET)
    public ResponseEntity<?> getProfile() {
        User user = userService.getCurrentUser();
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/api/profile", method = RequestMethod.POST)
    public ResponseEntity<?> setProfile(@RequestBody Profile profile) {
        //TODO 分开头像与Profile的数据，使得两者不再重复获得
        User user = userService.getCurrentUser();
        if (user != null && profile != null) {
            user.setProfile(profile);
            userRepo.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/api/reginfo", method = RequestMethod.GET)
    public ResponseEntity<?> getRegInfo() {
        User user = userRepo.findOne(1);
        if (user != null) {
            RegistrationInfo registrationInfo = user.getRegInfo();
            return new ResponseEntity<>(registrationInfo, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @JsonView(UserView.ProfileWithoutAvatar.class)
    @RequestMapping(value = "/api/friend/profile", method = RequestMethod.GET)
    public ResponseEntity<?> getFriendProfile() {
        User user = userRepo.findOne(1);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/api/friend/num", method = RequestMethod.GET)
    public ResponseEntity<?> getFriendNumber() {
        Api.Result result = Api.result(SUCCESS).param("num").value(123);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/follow/num", method = RequestMethod.GET)
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
}
