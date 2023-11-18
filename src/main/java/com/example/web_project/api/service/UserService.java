package com.example.web_project.api.service;

import com.example.web_project.api.model.User;
import com.example.web_project.api.model.UserDto;
import com.example.web_project.api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface UserService {

    public void saveUser(UserDto userDto);

    public User findUserByLogin(String login);

    public List<UserDto> findAllUsers();

    User getUser(long id_user);

    Iterable<User> getAllUsers();

    /*
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Iterable<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    public User getUser(Long id_user)
    {
        return userRepository.findById(id_user).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), new ArrayList<>());
    }
    */
}
