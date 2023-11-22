package com.example.web_project.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class EventDTO {
        private long id;
        private Date date;
        private String valueEvent;
        private String comment;
        private SerieDTO serie;
        private List<TagDTO> tags;


}
