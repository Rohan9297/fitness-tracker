package com.example.activityservice.DTO;

import java.time.LocalDateTime;
import com.example.activityservice.model.ActivityType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityResponse {
    private String id;
    private String userId;
    private ActivityType activityType;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;

    // private Map<String, Object> additionalMatrices;

    private LocalDateTime createATime;
    private LocalDateTime upDateTime;
}
