package com.example.config.converters.json;

import com.example.config.jsonviews.UserView;
import com.example.models.Profile;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonView;

public abstract class UserMixin {

    UserMixin() {
    }

    @JsonView(UserView.Profile.class)
    abstract String getUsername();

    @JsonProperty("email")
    @JsonView(UserView.Profile.class)
    abstract String getEmail();

    @JsonUnwrapped
    @JsonView(UserView.UserSummary.class)
    abstract Profile getProfile();
}
