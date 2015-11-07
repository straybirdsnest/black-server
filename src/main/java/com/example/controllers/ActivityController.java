package com.example.controllers;

import com.example.daos.ActivityRepo;
import com.example.models.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActivityController {
    @Autowired
    ActivityRepo activityRepo;

    @RequestMapping(value = "/api/activities")
    public ResponseEntity<?> getRecentActivities(){
        final PageRequest pageRequest = new PageRequest(0, 10, Sort.Direction.DESC,"startTime");
        Page<Activity> activities =  activityRepo.findAll(pageRequest);
        if(activities.getTotalElements()>0){
            List<Activity> activityList = activities.getContent();
            return new ResponseEntity<>(activityList, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}