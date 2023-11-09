package com.example.web_project.api.service;

import com.example.web_project.api.model.User;
import com.example.web_project.api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    public User getUser(Long id_user)
    {
        return userRepository.findById(id_user).orElseThrow(IllegalArgumentException::new);
    }
}
