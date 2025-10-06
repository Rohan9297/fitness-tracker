package com.example.fittness_app.Service;

import org.springframework.stereotype.Service;

import com.example.fittness_app.Dto.UserRegister;
import com.example.fittness_app.Dto.UserResponse;
import com.example.fittness_app.Mapper.UserMapper;
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
        return UserMapper.mapToUserResponse(savedUser);

    }

    public UserResponse getUserProfile(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        return UserMapper.mapToUserResponse(user);

    }

}
