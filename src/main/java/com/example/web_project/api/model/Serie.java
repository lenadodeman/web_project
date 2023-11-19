package com.example.web_project.api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_serie")
    private long id;

    @NotBlank(message = "Title cannot blank")
    @Size(max = 100, message = "Title length must be less than 100 characters")
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @NotBlank(message = "Description cannot blank")
    @Size(max = 255, message = "Description must be less than 255 characters")
    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @OneToMany(
            mappedBy = "serie",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private List<Event> eventList = new ArrayList<>();

}
