package com.example.activityservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final UserValidationService userValidationService;
    private final KafkaTemplate<String, Activity> kafkaTemplate;
    @Value("${kafka.topic.name:activity-events}")
    private String topicName;

    public ActivityResponse trackActivity(ActivityRequest request) {

        Boolean isValid = userValidationService.validateUser(request.getUserId());

        if (!isValid) {
            throw new RuntimeException("user Invalid" + request.getUserId());
        }

        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .activityType(request.getActivityType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMatrices(request.getAdditionalMatrices())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        kafkaTemplate.send(topicName, savedActivity.getUserId(), savedActivity);

        return ActivityMapper.activityToActivityResponse(savedActivity);
    }

}
