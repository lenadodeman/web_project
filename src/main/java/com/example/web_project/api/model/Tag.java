package com.example.web_project.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "Tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_tag")
    private long id;

    @NotBlank(message = "Label cannot be blank")
    @Size(max = 50, message = "Label length must be less than 50 characters")
    @Column(name = "label", nullable = false, length = 50)
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
