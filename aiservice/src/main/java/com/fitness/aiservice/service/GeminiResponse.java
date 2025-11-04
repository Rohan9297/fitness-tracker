package com.fitness.aiservice.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
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
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of("parts", new Object[] {
                                Map.of("text", details) // corrected key: should be "text", not "texts"
                        })
                });

        return webClient.post()
                .uri(geminiUri)
                .header("Content-Type", "application/json")
                .header("X-Goog-Api-Key", geminiApi)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
