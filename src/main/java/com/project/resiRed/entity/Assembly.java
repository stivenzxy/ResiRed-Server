package com.project.resiRed.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
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
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "assembly")
    private List<Survey> surveys;

    @ManyToMany
    @JoinTable(name = "attendance",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "assembly_id")
    )
    private List<User> users;
}
