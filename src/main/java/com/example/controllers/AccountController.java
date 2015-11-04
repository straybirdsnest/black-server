package com.example.controllers;

import com.example.Api;
import com.example.daos.UserRepo;
import com.example.models.Profile;
import com.example.models.RegistrationInfo;
import com.example.models.User;
import com.example.services.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.example.Api.ERR_PHONE_EXISTED;
import static com.example.Api.SUCCESS;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class AccountController {

    static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    UserRepo userRepo;

    @Autowired
    TokenService tokenService;

    /**
     * @param phone
     * @return
     */
    @RequestMapping(value = "/api/register", method = POST)
    public @ResponseBody Api.Result register(@RequestParam String phone){
        User u = userRepo.findOneByPhone(phone);
        if (u == null) {
            User user = new User(phone);
            RegistrationInfo registrationInfo = new RegistrationInfo();
            String remoteAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest().getRemoteAddr();
            LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("UTC"));
            registrationInfo.setRegIp(remoteAddress);
            registrationInfo.setRegTime(localDateTime);
            Profile profile = new Profile();
            profile.setGender(Profile.Gender.SECRET);
            user.setRegInfo(registrationInfo);
            user.setProfile(profile);
            userRepo.save(user);
            return Api.result(SUCCESS).param("token").value(tokenService.generateToken(user));
        }
        return Api.result(ERR_PHONE_EXISTED);
    }

    @RequestMapping(value = "/api/hello", method = GET)
    public String hello(HttpServletRequest request) {
        User user = (User) request.getAttribute("currentUser");
        return "hello " + user.getPhone();
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        return new ModelAndView("register");
    }
}
