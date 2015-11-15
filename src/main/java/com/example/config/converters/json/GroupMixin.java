package com.example.config.converters.json;

import com.example.config.jsonviews.GroupView;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

public abstract class GroupMixin {

    @JsonView(GroupView.GroupSummary.class)
    abstract Integer getId();

    @JsonView(GroupView.GroupSummary.class)
    abstract String getName();

    @JsonView(GroupView.GroupSummary.class)
    abstract String getIntro();

    @JsonView(GroupView.GroupSummary.class)
    @JsonProperty("logo")
    abstract String getLogoAccesssToken();

    @JsonView(GroupView.GroupSummary.class)
    @JsonProperty("page")
    abstract String getPageId();
}
