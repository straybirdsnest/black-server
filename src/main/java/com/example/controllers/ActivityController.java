package com.example.controllers;

import com.example.config.jsonviews.ActivityView;
import com.example.daos.ActivityRepo;
import com.example.models.Activity;
import com.example.models.Image;
import com.example.services.ImageService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@RestController
public class ActivityController {
    @Autowired
    ActivityRepo activityRepo;

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/api/activities", method = RequestMethod.GET)
    @JsonView(ActivityView.ActivitySummary.class)
    public ResponseEntity<?> getRecentActivities() {
        final PageRequest pageRequest = new PageRequest(0, 10, Sort.Direction.DESC, "startTime");
        Page<Activity> activities = activityRepo.findAll(pageRequest);
        if (activities.getTotalElements() > 0) {
            List<Activity> activityList = activities.getContent();
            Iterator<Activity> iterator = activityList.iterator();
            Activity activity = null;
            Image coverImage = null;
            while (iterator.hasNext()) {
                activity = iterator.next();
                coverImage = activity.getCoverImage();
                if (coverImage != null) {
                    activity.setCoverImageAccessToken(imageService.generateAccessToken(coverImage));
                }
            }
            return new ResponseEntity<>(activityList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
