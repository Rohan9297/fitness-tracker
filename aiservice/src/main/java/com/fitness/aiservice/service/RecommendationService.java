package com.fitness.aiservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repoLayer.AIRecommnedationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class RecommendationService {

    private final AIRecommnedationRepository aiRecommnedationRepository;

    public List<Recommendation> getUserRecommendation(String userId) {

        return aiRecommnedationRepository.findByUserId(userId);

    }

    public Recommendation getActivityRecommendation(String activityId) {

        return aiRecommnedationRepository.findByActivityId(activityId)
                .orElseThrow(() -> new RuntimeException("not found try with correct id" + activityId));
    }

}
