package com.project.resiRed.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Entity
@Data
@Table (name="assemblies")
public class Assembly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assemblyId;
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime time;

    @OneToMany
    private List<Survey> survey;
    @OneToMany
    private List<User> attendees;
}
