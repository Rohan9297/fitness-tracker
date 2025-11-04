package com.fitness.aiservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fitness.aiservice.model.Activity;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class ActivityListner {
    private final ActivityAIService activityAIService;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "activity-processor-group")
    public void process(Activity activity) {
        log.info("Received Activity for process: {}", activity.getUserId());
        activityAIService.generateRecommendation(activity);

    }
}
