package com.example.config.converters.json;

import com.example.config.jsonviews.UserView;
import com.example.models.Gender;
import com.example.models.Image;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Date;

public abstract class ProfileMixin {

    public ProfileMixin(){

    }

    @JsonView(UserView.UserSummary.class)
    abstract String getPhone();

    @JsonProperty("avatar")
    @JsonView(UserView.UserSummary.class)
    abstract String getAvatarAccessToken();

    @JsonProperty("background")
    @JsonView(UserView.Profile.class)
    abstract String getBackgroundImageAccessToken();

    @JsonView(UserView.Profile.class)
    abstract Gender getGender();

    @JsonView(UserView.Profile.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    abstract Date getBirthday();

    @JsonView(UserView.UserSummary.class)
    abstract String getSignature();

    @JsonView(UserView.Profile.class)
    abstract String getHometown();

    @JsonView(UserView.Profile.class)
    abstract String getUsername();

    @JsonView(UserView.Profile.class)
    abstract String getHighschool();

    @JsonView(UserView.Profile.class)
    @JsonProperty("college")
    abstract String getCollegeName();
    @JsonView(UserView.Profile.class)
    @JsonProperty("college")
    abstract void setCollegeName();

    @JsonView(UserView.Profile.class)
    @JsonProperty("academy")
    abstract String getAcademyName();

    @JsonView(UserView.Profile.class)
    abstract String getGrade();

    @JsonView(UserView.Profile.class)
    abstract Image getAvatar();
}
