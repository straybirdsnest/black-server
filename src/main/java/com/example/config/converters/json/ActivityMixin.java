package com.example.config.converters.json;

import com.example.config.jsonviews.ActivityView;
import com.example.models.Activity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Date;
import java.util.Set;

public abstract class ActivityMixin {
    @JsonView(ActivityView.ActivitySummary.class)
    abstract Integer getId();

    @JsonProperty("coverImage")
    @JsonView(ActivityView.ActivitySummary.class)
    abstract String getCoverImageAccessToken();

    @JsonView(ActivityView.ActivitySummary.class)
    abstract String getTitle();

    @JsonView(ActivityView.ActivityDetails.class)
    abstract String getContent();

    @JsonView(ActivityView.ActivitySummary.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    abstract Date getStartTime();

    @JsonView(ActivityView.ActivitySummary.class)
    abstract String getLocation();

    @JsonView(ActivityView.ActivitySummary.class)
    @JsonProperty("game")
    abstract String getGameName();

    @JsonView(ActivityView.ActivityDetails.class)
    abstract Activity.Type getType();

    @JsonView(ActivityView.ActivityDetails.class)
    abstract String getStatus();

    @JsonView(ActivityView.ActivityDetails.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    abstract Date getRegistrationDeadline();

    @JsonView(ActivityView.ActivityDetails.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    abstract Date getEndTime();

    @JsonView(ActivityView.ActivityDetails.class)
    @JsonProperty("photos")
    abstract Set<String> getPhotosAccessToken();

    @JsonView(ActivityView.ActivityDetails.class)
    @JsonProperty("group")
    abstract Integer getGroupId();

}
