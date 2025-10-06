package com.example.fittness_app.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fittness_app.Dto.UserRegister;
import com.example.fittness_app.Dto.UserResponse;
import com.example.fittness_app.Service.UserService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private UserService UserService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegister register) {
        // TODO: process POST request

        return ResponseEntity.ok(UserService.register(register));
    }

}
