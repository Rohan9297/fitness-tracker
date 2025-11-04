package com.example.UserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceAppApplication {

	public static void main(String[] args) {

		SpringApplication.run(UserServiceAppApplication.class, args);

	}

}
