package com.fitness.aiservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class ActivityAIService {

    private final GeminiResponse geminiResponse;

    public Recommendation generateRecommendation(Activity activity) {
        log.info("i am in genrate response method");

        String prompt = createPrompt(activity);
        log.info("prompt created succeessfully");

        String aiResponse = geminiResponse.getRecommendation(prompt);

        // log.info("ai generated response: {}", aiResponse);

        return processAiResponse(activity, aiResponse);

    }

    private Recommendation processAiResponse(Activity activity, String aiResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(aiResponse);
            JsonNode textNode = rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .get("parts")
                    .get(0)
                    .path("text");

            String jsonContent = textNode.asText()
                    .replaceAll("```json\\n", "")
                    .replaceAll("\\n", "")
                    .trim();

            log.info(jsonContent);
            JsonNode analysisJson = objectMapper.readTree(jsonContent);
            JsonNode analysisNode = analysisJson.path("analysis");

            StringBuilder fullAnalysis = new StringBuilder();

            addAnalysisSection(fullAnalysis, analysisNode, "overall", "overall:");
            addAnalysisSection(fullAnalysis, analysisNode, "pace", "pace:");
            addAnalysisSection(fullAnalysis, analysisNode, "heartRate", "heart Rate:");
            addAnalysisSection(fullAnalysis, analysisNode, "caloriesBurned", "calories Burned:");

            List<String> improvments = exractImprovements(analysisJson.path("improvements"));
            List<String> suggetions = exractSuggestions(analysisJson.path("suggestions"));
            List<String> safety = exractSafety(analysisJson.path("safety"));

            return Recommendation.builder()
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .type(activity.getActivityType().toString())
                    .recommendations(fullAnalysis.toString())
                    .improvements(improvments)
                    .suggestions(suggetions)
                    .safety(safety)
                    .createdAt(LocalDateTime.now())
                    .build();

        } catch (Exception e) {
            log.info(e.getMessage());

            return defualtRecommendation(activity);
        }

    }

    private Recommendation defualtRecommendation(Activity activity) {
        return Recommendation.builder()
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .type(activity.getActivityType().toString())
                .recommendations("unable to generate anlysis")
                .improvements(Collections.singletonList("unable to generate improvements"))
                .suggestions(List.of("unable to generate suggestions"))
                .safety(List.of("unable to generate safety guidelines"))
                .createdAt(LocalDateTime.now())
                .build();

    }

    private List<String> exractSuggestions(JsonNode suggestionNode) {
        List<String> suggestions = new ArrayList<>();
        if (suggestionNode.isArray()) {
            for (JsonNode improvement : suggestionNode) {
                String workout = improvement.path("workout").asText();
                String description = improvement.path("description").asText();

                suggestions.add(String.format("%s:%s", workout, description));
            }
        }
        return suggestions.isEmpty() ? List.of("no response from the assistant try some time letter") : suggestions;
    }

    private List<String> exractSafety(JsonNode safetyNode) {
        List<String> safetyList = new ArrayList<>();
        if (safetyNode.isArray()) {
            String result = safetyNode.get(0).asText();
            safetyList.add(result);

            // safetyNode.forEach(item-> safetyList.add(item.asText()));
        }
        return safetyList.isEmpty() ? Collections.singletonList("no response from the assistant try again later")
                : safetyList;
    }

    private List<String> exractImprovements(JsonNode imrovementNode) {

        List<String> improvements = new ArrayList<>();
        if (imrovementNode.isArray()) {
            for (JsonNode improvement : imrovementNode) {
                String area = improvement.path("area").asText();
                String details = improvement.path("recommendation").asText();

                improvements.add(String.format("%s:%s", area, details));
            }
        }
        return improvements.isEmpty() ? List.of("no response from the assistant try some time letter") : improvements;

    }

    private void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix) {

        if (!analysisNode.path(key).isMissingNode()) {
            fullAnalysis.append(prefix)
                    .append(analysisNode.path(key).asText())
                    .append("\n\n");
        }

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
