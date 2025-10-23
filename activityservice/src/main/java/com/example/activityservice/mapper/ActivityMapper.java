package com.example.activityservice.mapper;

import com.example.activityservice.DTO.ActivityResponse;
import com.example.activityservice.model.Activity;

public class ActivityMapper {

    public static Activity activityResponsetoActivity(ActivityResponse activityResponse) {

        Activity activity = new Activity(

                activityResponse.getUserId(),
                activityResponse.getActivityType(),
                activityResponse.getDuration(),
                activityResponse.getCaloriesBurned(),
                activityResponse.getStartTime(),
                activityResponse.getAdditionalMatrices(),
                activityResponse.getCreateATime(),
                activityResponse.getUpDateTime());

        return activity;
    }

    public static ActivityResponse activityToActivityResponse(Activity activity) {
        ActivityResponse activityResponse = new ActivityResponse(
                activity.getUserId(),
                activity.getActivityType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getStartTime(),
                activity.getAdditionalMatrices(),
                activity.getCreateATime(),
                activity.getUpDateTime());

        return activityResponse;
    }
}
