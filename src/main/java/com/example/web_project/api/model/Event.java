package com.example.web_project.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "Events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_event")
    private long id;

    @Column(name = "event_date")
    private Date date;
    @Column(name = "value_event")
    private String valueEvent;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(
            cascade = CascadeType.ALL)
    @JoinColumn(name = "id_serie")
    @JsonBackReference
    private Serie serie;




    //Plusieurs events peuvent être rattachés à plusieurs Tags et plusieurs tags peuvent être rattachés à plusieurs event ?
}
