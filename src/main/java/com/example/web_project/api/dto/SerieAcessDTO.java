package com.example.web_project.api.dto;

import com.example.web_project.api.model.Serie;
import com.example.web_project.api.model.User;

public class SerieAcessDTO {
    private long id;
    private SerieDTO serie;
    private UserDTO user;
    private String accessType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SerieDTO getSerie() {
        return serie;
    }

    public void setSerie(SerieDTO serie) {
        this.serie = serie;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }


}
