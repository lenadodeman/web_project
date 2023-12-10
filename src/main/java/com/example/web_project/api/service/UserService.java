package com.example.web_project.api.service;

import com.example.web_project.api.dto.SerieDTO;
import com.example.web_project.api.dto.UserDTO;
import com.example.web_project.api.mapper.UserMapper;
import com.example.web_project.api.model.Event;
import com.example.web_project.api.model.Serie;
import com.example.web_project.api.model.User;
import com.example.web_project.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper = UserMapper.INSTANCE;

    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findUserById(final long id_user)
    {
        return userRepository.findById(id_user)
                .orElseThrow(
                        () -> new EntityNotFoundException("Event not found with id: " + id_user)
                );
    }

    public List<UserDTO> getAllUsers()
    {
        List<User> userList = userRepository.findAll();
        return userMapper.toDTOList(userRepository.findAll());
    }

    public UserDTO getUser(Long id_user)
    {
        return userMapper.toDTO(findUserById(id_user));
    }

    public UserDTO addUser(UserDTO userDTO)
    {
        if(userRepository.existsById(userDTO.getId()))
        {
            throw new IllegalStateException("User with id " + userDTO.getId() + " already exists");
        }

        User userToAdd = userMapper.toDomain(userDTO);

        userToAdd.setPassword(passwordEncoder.encode(userToAdd.getPassword()));

        return userMapper.toDTO(userRepository.save(userToAdd));
    }

    public void deleteUser(final long id_user)
    {
        if(!userRepository.existsById(id_user))
        {
            throw new IllegalStateException("User with id " + id_user + " already exists");
        }
        userRepository.deleteById(id_user);
    }

    @Transactional
    public UserDTO updateUser(UserDTO userDTO)
    {
        if(!userRepository.existsById(userDTO.getId()))
        {
            throw new EntityNotFoundException("User not found with id: " + userDTO.getId());
        }

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        User userToUpdate = userMapper.toDomain(userDTO);

        return userMapper.toDTO(userRepository.save(userToUpdate));
    }

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            // Utilisez le nom d'utilisateur pour récupérer l'utilisateur de votre base de données
            User user = userRepository.findUserByUsername(username);

            return user.getId();
        }

        return null;
    }
}
