package com.example.config.converters.json;

import com.example.config.jsonviews.UserView;
import com.example.models.Profile;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import java.time.LocalDate;

public abstract class ProfileMixin {
    @JsonView(UserView.Profile.class)
    abstract String getNickename();

    @JsonView(UserView.UserSummary.class)
    abstract String getRealName();;

    @JsonView(UserView.Profile.class)
    abstract String getIdCard();

    @JsonView(UserView.Profile.class)
    abstract Profile.Gender getGender();

    @JsonView(UserView.Profile.class)
    abstract LocalDate getBirthday();

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
}
