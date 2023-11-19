package com.example.web_project.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_user")
    private Long id_user;

    @NotBlank(message = "Login cannot be blank")
    @Size(max = 30, message = "Login length must be less than 30 characters")
    @Column(name = "login", nullable = false, length = 30)
    private String login;

    @NotBlank(message = "First name cannot be blank")
    @Size(max = 50, message = "First name length must be less than 50 characters")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 50, message = "Last name length must be less than 50 characters")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

}
