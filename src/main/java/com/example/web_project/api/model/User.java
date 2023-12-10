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
    private Long id;

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 30, message = "Username length must be less than 30 characters")
    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @NotBlank(message = "Password name cannot be blank")
    @Size(max = 400, message = "Password name length must be less than 400 characters")
    @Column(name = "password", nullable = false, length = 50)
    private String password;

    private String role;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private List<SerieAccess> serieAccesses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<SerieAccess> getSerieAccesses() {
        return serieAccesses;
    }

    public void setSerieAccesses(List<SerieAccess> serieAccesses) {
        this.serieAccesses = serieAccesses;
    }





}
