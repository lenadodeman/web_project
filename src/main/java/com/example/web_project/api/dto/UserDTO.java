package com.example.web_project.api.dto;


import com.example.web_project.api.model.SerieAccess;

import java.util.List;

public class UserDTO {

    private long id;
    private String username;
    private String password;
    private String role;

    private List<SerieAcessDTO> serieAccesses;
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

    public List<SerieAcessDTO> getSerieAccesses() {
        return serieAccesses;
    }

    public void setSerieAccesses(List<SerieAcessDTO> serieAccesses) {
        this.serieAccesses = serieAccesses;
    }





}
