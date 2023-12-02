package com.example.web_project.api.service;

import com.example.web_project.api.model.dbUser;
//import com.example.web_project.api.dto.UserDto;
import com.example.web_project.api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository){this.userRepository = userRepository;}
    public dbUser getUser(Long id_user)
    {
        return userRepository.findById(id_user).orElseThrow(IllegalArgumentException::new);
    }

    public Iterable<dbUser> getAllUsers()
    {
        return userRepository.findAll();
    }

}
