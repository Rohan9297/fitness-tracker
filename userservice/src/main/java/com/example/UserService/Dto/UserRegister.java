package com.example.UserService.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegister {

    @NotBlank(message = "email should be non empty")
    @Email(message = "invalid email format")
    private String email;
    @NotBlank(message = "write a password dont be oversmart")
    @Size(min = 6, message = "invalid password")
    private String password;
    private String firstname;
    private String lastname;
}
