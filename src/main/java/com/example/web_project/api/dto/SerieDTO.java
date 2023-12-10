package com.example.web_project.api.dto;


import com.example.web_project.api.model.SerieAccess;

import java.util.List;

public class SerieDTO {

    private long id;
    private String title;
    private String description;

    private List<EventDTO> eventList;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<EventDTO> getEventList() {
        return eventList;
    }

    public void setEventList(List<EventDTO> eventList) {
        this.eventList = eventList;
    }



}

