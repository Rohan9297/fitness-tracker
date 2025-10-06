package com.example.fittness_app.Dto;

import java.time.LocalDateTime;

import com.example.fittness_app.Models.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
