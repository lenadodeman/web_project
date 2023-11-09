package com.example.web_project.api.controller;

import com.example.web_project.api.model.User;
import com.example.web_project.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/users/{id_user}")
    public Object getUser(@PathVariable("id_user") long id_user)
    {
        User user = userService.getUser(id_user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    public Iterable<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

}
