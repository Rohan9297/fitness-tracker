package com.fitness.aiservice.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GeminiResponse {

        private final WebClient webClient;

        @Value("${gemini.api.key}")
        private String geminiApi;

        @Value("${gemini.api.url}")
        private String geminiUri;

        @Autowired
        public GeminiResponse(WebClient.Builder wBuilder) {
                this.webClient = wBuilder.build();
        }

        public String getRecommendation(String details) {
                log.info("in the getRecommendation");
                Map<String, Object> requestBody = Map.of(
                                "contents", List.of(
                                                Map.of(
                                                                "role", "user",
                                                                "parts", List.of(
                                                                                Map.of("text", details)))));

                log.info("returning value");

                log.info("Sending request to Gemini API");

                try {
                        return webClient.post()
                                        .uri(geminiUri)
                                        .header("Content-Type", "application/json")
                                        .header("X-Goog-Api-Key", geminiApi)
                                        .bodyValue(requestBody)
                                        .retrieve()
                                        .bodyToMono(String.class)
                                        .block();
                } catch (WebClientResponseException e) {
                        log.error("Gemini API error: {}", e.getResponseBodyAsString());
                        throw e;
                }
        }
}
