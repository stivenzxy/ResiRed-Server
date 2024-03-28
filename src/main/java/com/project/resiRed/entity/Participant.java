package com.project.resiRed.entity;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table (name="participants")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "assembly_id")
    private Assembly assembly;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean attendance;
}
