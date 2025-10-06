package com.example.fittness_app.Dto;

import java.time.LocalDateTime;

import com.example.fittness_app.Models.UserRole;

import lombok.Data;

@Data
public class UserResponse {

    private String id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserRole role = UserRole.USER;
}
