package com.example.UserService.Mapper;

import com.example.UserService.Dto.UserResponse;
import com.example.UserService.Models.User;

public class UserMapper {

    public static User mapToUser(UserResponse userResponse) {

        User user = new User(
                userResponse.getId(),
                userResponse.getEmail(),
                userResponse.getPassword(),
                userResponse.getFirstname(),
                userResponse.getLastname(),
                userResponse.getCreatedAt(),
                userResponse.getUpdatedAt(),
                userResponse.getRole());

        return user;
    }

    public static UserResponse mapToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getFirstname(),
                user.getLastname(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getRole());
    }
}
