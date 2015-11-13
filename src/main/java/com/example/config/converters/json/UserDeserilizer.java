package com.example.config.converters.json;

import com.example.models.Academy;
import com.example.models.College;
import com.example.models.Profile;
import com.example.models.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class UserDeserilizer extends JsonDeserializer<User> {

    private static final Logger logger = LoggerFactory.getLogger(UserDeserilizer.class);

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode root = jsonParser.getCodec().readTree(jsonParser);
        String phone = root.get("phone").asText();
        String username = root.get("username").asText();
        String email = root.get("email").asText();
        JsonNode birthdayNode = root.get("birthday");
//        LocalDate birthday = LocalDate.of(birthdayNode.get(0).asInt(),
//                birthdayNode.get(1).asInt(), birthdayNode.get(2).asInt());
        String collegeName = root.get("college").asText();
        String academyName = root.get("academy").asText();
        String grade = root.get("grade").asText();
        String signature = root.get("signature").asText();
        String hometown = root.get("hometown").asText();
        String highschool = root.get("highschool").asText();
        String gender = root.get("gender").asText();

        User user = new User();
        user.setPhone(phone);
        user.setEmail(email);

        Profile profile = new Profile();
        profile.setUsername(username);
//        profile.setBirthday(birthday);

        College college = new College();
        college.setName(collegeName);
        profile.setCollege(college);

        Academy academy = new Academy();
        academy.setName(academyName);
        profile.setAcademy(academy);

        switch (gender) {
            case "MALE":
                profile.setGender(Profile.Gender.MALE);
                break;
            case "FEMALE":
                profile.setGender(Profile.Gender.FEMALE);
                break;
            case "SECRET":
                profile.setGender(Profile.Gender.SECRET);
                break;
            default:
                throw new IOException("unknown gender type of " + gender);
        }

        profile.setGrade(grade);
        profile.setSignature(signature);
        profile.setHometown(hometown);
        profile.setHighschool(highschool);
        user.setProfile(profile);

        return user;
    }
}
