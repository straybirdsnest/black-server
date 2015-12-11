package org.team10424102.blackserver.config.propertyeditors;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.team10424102.blackserver.daos.AcademyRepo;
import org.team10424102.blackserver.daos.CollegeRepo;
import org.team10424102.blackserver.models.*;
import org.team10424102.blackserver.services.ImageService;

import java.text.SimpleDateFormat;

public class UserResolver implements HandlerMethodArgumentResolver {
    private final CollegeRepo collegeRepo;
    private final AcademyRepo academyRepo;
    private final ImageService imageService;

    public UserResolver(CollegeRepo collegeRepo, AcademyRepo academyRepo, ImageService imageService) {
        this.collegeRepo = collegeRepo;
        this.academyRepo = academyRepo;
        this.imageService = imageService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {

        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setEmail(request.getParameter("email"));

        user.setNickname(request.getParameter("nickname"));
        String genderName = request.getParameter("gender");
        if (genderName != null) {
            user.setGender(Gender.valueOf(genderName.toUpperCase()));
        }
        // TODO avatar
        String avatarToken = request.getParameter("avatar");
        if (avatarToken != null) {
            Image avatar = imageService.getImageFromAccessToken(avatarToken);
            user.setAvatar(avatar);
        }
        // TODO background
        String backgroundToken = request.getParameter("background");
        if (backgroundToken != null) {
            Image background = imageService.getImageFromAccessToken(backgroundToken);
            user.setBackground(background);
        }
        String birthdayStr = request.getParameter("birthday");
        if (birthdayStr != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            user.setBirthday(df.parse(birthdayStr));
        }
        user.setSignature(request.getParameter("signature"));
        user.setHometown(request.getParameter("hometown"));
        user.setHighschool(request.getParameter("highschool"));
        String collegeName = request.getParameter("college");
        if (collegeName != null) {
            College college = collegeRepo.findOneByName(collegeName);
            user.setCollege(college);
        }
        String academyName = request.getParameter("academy");
        if (academyName != null) {
            Academy academy = academyRepo.findOneByName(academyName);
            user.setAcademy(academy);
        }
        user.setGrade(request.getParameter("grade"));
        return user;
    }
}