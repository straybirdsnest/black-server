package com.example.controllers;

import com.example.config.jsonviews.ActivityView;
import com.example.daos.ActivityRepo;
import com.example.models.Activity;
import com.example.models.Image;
import com.example.models.User;
import com.example.services.ImageService;
import com.example.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RestController
public class ActivityController {

    @Autowired
    UserService userService;

    @Autowired
    ActivityRepo activityRepo;

    @Autowired
    ImageService imageService;

    /**
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/api/activities/{type}", method = RequestMethod.GET)
    @JsonView(ActivityView.ActivitySummary.class)
    public ResponseEntity<?> getRecentActivities(@PathVariable String type, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 5;
        }
        final PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.DESC, "startTime");
        Page<Activity> activities = activityRepo.findAll(pageRequest);
        if (activities.getTotalElements() <= 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
    }

    @RequestMapping(value = "/api/activity", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateParticleActivity(@RequestParam(value = "hh", required = false) String hh) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * 获取当前用户所举办的活动
     *
     * @return
     */
    @RequestMapping(value = "/api/user/activities", method = RequestMethod.GET)
    @JsonView(ActivityView.ActivitySummary.class)
    public ResponseEntity<?> getUserActivity(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Set<Activity> activities = activityRepo.findByPromoter(currentUser);
        if (activities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

}
