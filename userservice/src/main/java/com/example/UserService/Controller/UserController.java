package com.example.UserService.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.UserService.Dto.UserRegister;
import com.example.UserService.Dto.UserResponse;
import com.example.UserService.Service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private UserService UserService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegister register) {

        return ResponseEntity.ok(UserService.register(register));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable String userId) {

        return ResponseEntity.ok(UserService.getUserProfile(userId));

    }

    @GetMapping("/{userId}/validate")
    public ResponseEntity<Boolean> existById(@PathVariable String userId) {
        log.info("activity api call is hitted");
        return ResponseEntity.ok(UserService.existById(userId));
    }
}
