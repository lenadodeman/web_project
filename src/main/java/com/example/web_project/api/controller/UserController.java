package com.example.web_project.api.controller;

import com.example.web_project.api.model.User;
import com.example.web_project.api.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> listAll() {
        return userRepository.findAll();
    }

}
