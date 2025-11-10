package com.fitness.aiservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repoLayer.AIRecommnedationRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
class ActivityListner {
    private final ActivityAIService activityAIService;
    private final AIRecommnedationRepository aiRecommnedationRepository;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "activity-processor-group")
    public void process(Activity activity) {
        log.info("Received Activity for process: {}", activity.getUserId());

        try {
            Recommendation recommendation = activityAIService.generateRecommendation(activity);
            aiRecommnedationRepository.save(recommendation);
        } catch (Exception e) {
            log.error("failed to generate the response from the gemini");
            e.getMessage();
            log.error("error caused by:", e);
        }

    }
}
