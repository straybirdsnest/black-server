package com.example.services;

import com.example.models.Activity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ActivityService {

    List<Activity> getRecommendedActivities(Pageable pageable);

    List<Activity> getSameSchoolActivities(Pageable pageable);

    List<Activity> getFriendsActivities(Pageable pageable);

    List<Activity> getFocusesActivities(Pageable pageable);

    List<Activity> getMyActivities(Pageable pageable);

    void createActivity(Activity activity);

    void deleteActivity(long activityId);

    Activity findOne(long activityId);
}
