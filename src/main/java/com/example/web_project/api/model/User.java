package com.example.web_project.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_user")
    private Long id_user;

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 30, message = "Username length must be less than 30 characters")
    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @NotBlank(message = "Password name cannot be blank")
    @Size(max = 50, message = "Password name length must be less than 50 characters")
    @Column(name = "password", nullable = false, length = 50)
    private String password;

    private String role;

    @ManyToMany(
            mappedBy = "users",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            fetch = FetchType.EAGER
    )
    private List<Serie> series;

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
