package com.example.web_project.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "label")
    private String label;


}
