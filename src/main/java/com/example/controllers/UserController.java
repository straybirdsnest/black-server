package com.example.controllers;

import com.example.Api;
import com.example.daos.UserRepo;
import com.example.models.Profile;
import com.example.models.User;
import com.example.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.example.Api.ERR_DATA_NOT_FOUND;
import static com.example.Api.SUCCESS;

@RestController
public class UserController {
    static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/api/avatar", method = RequestMethod.GET)
    public ResponseEntity<?> getAvatar()
    {
        User user = userService.getCurrentUser();
        if(user!=null && user.profile != null && user.profile.getAvatar() != null) {
            byte[] avatar = user.profile.getAvatar();
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
            ImageIO.write(bufferedImage,"PNG",byteArrayOutputStream);
            byte[] avatar = byteArrayOutputStream.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(avatar.length);
            return new ResponseEntity<byte[]>(avatar,headers,HttpStatus.OK);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/api/avatar", method = RequestMethod.POST)
    public @ResponseBody Api.Result setAvatar(byte[] avatar)
    {
        User user = userService.getCurrentUser();
        if(user!= null && avatar!= null){
            user.profile.setAvatar(avatar);
            userRepo.save(user);
            return Api.result(SUCCESS);
        }
        return Api.result(ERR_DATA_NOT_FOUND);
    }

    @RequestMapping(value = "/api/profile", method = RequestMethod.GET)
    public ResponseEntity<?> getProfile() {
        User user = userService.getCurrentUser();
        if (user != null) {
            if (user.profile != null) {
                return new ResponseEntity<>(user.profile, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/api/profile", method = RequestMethod.POST)
    public ResponseEntity<?> setProfile(@RequestBody Profile profile)
    {
        User user = userService.getCurrentUser();
        if(user!= null && profile!= null){
            user.profile = profile;
            userRepo.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
