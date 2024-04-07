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

    @OneToMany(mappedBy = "assembly")
    private List<Survey> surveys;

    @ManyToMany
    @JoinTable(name = "attendance",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "assembly_id")
    )
    private List<User> users;
}
