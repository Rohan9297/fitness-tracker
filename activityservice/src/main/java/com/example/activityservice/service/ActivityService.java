package com.example.activityservice.service;

import org.springframework.stereotype.Service;

import com.example.activityservice.DTO.ActivityRequest;
import com.example.activityservice.DTO.ActivityResponse;
import com.example.activityservice.mapper.ActivityMapper;
import com.example.activityservice.model.Activity;
import com.example.activityservice.repository.ActivityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityResponse trackActivity(ActivityRequest request) {

        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .activityType(request.getActivityType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMatrices(request.getAdditionalMatrices())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        return ActivityMapper.activityToActivityResponse(savedActivity);
    }

}
