package com.fitness.aiservice.repoLayer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fitness.aiservice.model.Recommendation;

@Repository
public interface AIRecommnedationRepository extends MongoRepository<Recommendation, String> {

    List<Recommendation> findByUserId(String userId); // Updated method name

    Optional<Recommendation> findByActivityId(String activityId);
}