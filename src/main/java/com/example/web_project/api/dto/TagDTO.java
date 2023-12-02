package com.example.web_project.api.dto;

import com.example.web_project.api.model.Event;


import java.util.List;

public class TagDTO {

    private long id;
    private String label;
    List<EventDTO> events;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }
}
