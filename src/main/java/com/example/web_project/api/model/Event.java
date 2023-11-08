package com.example.web_project.api.model;

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
    private long id;

    @Column(name = "event_date")
    private Date date;
    @Column(name = "value_event")
    private String valueEvent;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "serie")
    private Serie serie;



    //@OneToMany
    @Transient
    private ArrayList<Tag> TagsList;

    //Plusieurs events peuvent être rattachés à plusieurs Tags et plusieurs tags peuvent être rattachés à plusieurs produits ?
}
