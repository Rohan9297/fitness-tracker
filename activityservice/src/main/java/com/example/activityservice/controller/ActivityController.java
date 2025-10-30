package com.example.activityservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.activityservice.DTO.ActivityRequest;
import com.example.activityservice.DTO.ActivityResponse;
import com.example.activityservice.service.ActivityService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RequestMapping("/user/activity")
@RestController
public class ActivityController {

    public final ActivityService activityservice;

    @PostMapping("/request")
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest request) {

        return ResponseEntity.ok(activityservice.trackActivity(request));
    }

}
