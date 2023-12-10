package com.example.web_project.api.controller;

import com.example.web_project.api.dto.SerieDTO;
import com.example.web_project.api.dto.UserDTO;
import com.example.web_project.api.model.User;
import com.example.web_project.api.service.UserService;
import org.mapstruct.control.MappingControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/{id_user}")
    public Object getUser(@PathVariable("id_user") long id_user)
    {
        UserDTO userDTO = userService.getUser(id_user);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers()
    {
        List<UserDTO> foundUsers = userService.getAllUsers();
        return new ResponseEntity<>(foundUsers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO)
    {
        UserDTO addedUser = userService.addUser(userDTO);
        return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id_user}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable long id_user)
    {
        userService.deleteUser(id_user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping()
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO)
    {
        UserDTO updatedUser = userService.updateUser(userDTO);
        return new ResponseEntity<UserDTO>(updatedUser, HttpStatus.OK);
    }




}
