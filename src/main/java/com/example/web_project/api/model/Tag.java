package com.example.web_project.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_tag")
    private long id;

    @Column(name = "label")
    private String label;


    @ManyToMany(
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE

        }
    )
    @JoinTable (
            name="Associate",
            joinColumns = @JoinColumn(name = "id_tag"),
            inverseJoinColumns = @JoinColumn(name = "id_event")

    )
    @JsonIgnoreProperties("tags")
    private List<Event> events = new ArrayList<>();


}
