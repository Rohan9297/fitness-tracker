package com.fitness.aiservice.service;

import org.springframework.stereotype.Service;

import com.fitness.aiservice.model.Activity;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class ActivityAIService {

    private final GeminiResponse geminiResponse;

    public void generateRecommendation(Activity activity) {

        String prompt = createPrompt(activity);

        String aiResponse = geminiResponse.getRecommendation(prompt);

        log.info("ai generated response:", aiResponse);

    }

    public String createPrompt(Activity activity) {

        return String.format("\r\n" + //
                "Analyze this fitness activity and provide detailed recommendations in the following EXACT JSON format:\r\n"
                + //
                "        {\r\n" + //
                "          \"analysis\": {\r\n" + //
                "            \"overall\": \"Overall analysis here\",\r\n" + //
                "            \"pace\": \"Pace analysis here\",\r\n" + //
                "            \"heartRate\": \"Heart rate analysis here\",\r\n" + //
                "            \"caloriesBurned\": \"Calories analysis here\"\r\n" + //
                "          },\r\n" + //
                "          \"improvements\": [\r\n" + //
                "            {\r\n" + //
                "              \"area\": \"Area name\",\r\n" + //
                "              \"recommendation\": \"Detailed recommendation\"\r\n" + //
                "            }\r\n" + //
                "          ],\r\n" + //
                "          \"suggestions\": [\r\n" + //
                "            {\r\n" + //
                "              \"workout\": \"Workout name\",\r\n" + //
                "              \"description\": \"Detailed workout description\"\r\n" + //
                "            }\r\n" + //
                "          ],\r\n" + //
                "          \"safety\": [\r\n" + //
                "            \"Safety point 1\",\r\n" + //
                "            \"Safety point 2\"\r\n" + //
                "          ]\r\n" + //

                "        }\r\n" + //
                "\r\n" + //
                "        Analyze this activity:\r\n" + //
                "        Activity Type: %s\r\n" + //
                "        Duration: %d minutes\r\n" + //
                "        Calories Burned: %d\r\n" + //
                "        Additional Metrics: %s\r\n" + //
                "        \r\n" + //
                "        Provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.\r\n"
                + //
                "        Ensure the response follows the EXACT JSON format shown above.\r\n" + //
                "        \"\"\"",
                activity.getActivityType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalMatrices());
    }

}
