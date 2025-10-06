package com.example.fittness_app.Service;

import org.springframework.stereotype.Service;

import com.example.fittness_app.Dto.UserRegister;
import com.example.fittness_app.Dto.UserResponse;
import com.example.fittness_app.Models.User;
import com.example.fittness_app.RepoLayer.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;

    public UserResponse register(UserRegister register) {

        if (userRepository.existsByEmail(register.getEmail())) {
            throw new RuntimeException("Email already existed /n try another email");
        }
        User user = new User();

        user.setEmail(register.getEmail());

        user.setFirstname(register.getFirstname());
        user.setLastname(register.getLastname());
        user.setPassword(register.getPassword());

        User savedUser = userRepository.save(user);
        UserResponse userResponse = new UserResponse();

        userResponse.setFirstname(savedUser.getFirstname());
        userResponse.setLastname(savedUser.getLastname());
        userResponse.setPassword(savedUser.getPassword());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setId(savedUser.getId());
        userResponse.setCreatedAt(savedUser.getCreatedAt());

        userResponse.setUpdatedAt(savedUser.getUpdatedAt());
        return userResponse;
    }

}
