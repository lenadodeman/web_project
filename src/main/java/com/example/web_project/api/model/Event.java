package com.example.web_project.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Date cannot be blank")
    @Column(name = "event_date", nullable = false)
    private Date date;

    @NotBlank(message = "Value event cannot be blank")
    @Min(value = 0, message = "Value must be positive")
    @Column(name = "value_event", nullable = false)
    private int valueEvent;

    @Size(max = 255, message = "Comment must be less than 255 characters")
    @Column(name = "comment", length = 255)
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
