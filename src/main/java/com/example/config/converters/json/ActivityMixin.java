package com.example.config.converters.json;

import com.example.config.jsonviews.ActivityView;
import com.example.models.Activity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import java.time.LocalDateTime;

public abstract class ActivityMixin {
    @JsonProperty("coverImage")
    @JsonView(ActivityView.ActivitySummary.class)
    abstract String getCoverImageAccessToken();

    @JsonView(ActivityView.ActivitySummary.class)
    abstract LocalDateTime getStartTime();

    @JsonView(ActivityView.ActivitySummary.class)
    abstract String getLocation();

    @JsonView(ActivityView.ActivitySummary.class)
    abstract Activity.Type getType();
}
