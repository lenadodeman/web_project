package com.example.web_project.api.dto;

import com.example.web_project.api.model.Event;

import java.util.List;

public class TagDTO {

    private long id;
    private String label;

    List<EventDTO> events;
}
