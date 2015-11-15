package com.example.config.converters.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class GroupMixin {

    abstract Integer getId();
    abstract String getName();
    abstract String getIntro();
    @JsonProperty("logo")
    abstract String getLogoAccesssToken();
    @JsonProperty("page")
    abstract String getPageId();
}
