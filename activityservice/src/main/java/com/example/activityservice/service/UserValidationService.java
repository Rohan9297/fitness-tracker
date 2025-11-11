package com.example.activityservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserValidationService {

    private final WebClient userServiceWebClient;

    public Boolean validateUser(String userId) {
        try {
            log.info("calling for an user validation:");
            return userServiceWebClient.get()
                    .uri("/api/user/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .doOnError(e -> log.error("❌ WebClient error while calling user-service: {}", e.getMessage()))
                    .block();
        } catch (WebClientResponseException e) {
            log.info("user is invalid {} :", userId);
            // e.printStackTrace();
        }

        return false;

    }

}
