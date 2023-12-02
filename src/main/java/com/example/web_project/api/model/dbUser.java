package com.example.web_project.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "dbUsers")
public class dbUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_user")
    private Long id_user;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

}
