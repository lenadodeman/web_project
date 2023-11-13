package com.example.web_project.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinColumn(name = "id_serie")
    @JsonBackReference
    private Serie serie;


    @ManyToMany(
            fetch = FetchType.LAZY,
//            mappedBy = "events",
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            }
    )
    @JoinTable (
            name="Associate",
            joinColumns = @JoinColumn(name = "id_event"),
            inverseJoinColumns = @JoinColumn(name = "id_tag")
    )
    @JsonIgnoreProperties("events")
    private List<Tag> tags = new ArrayList<>();


}
