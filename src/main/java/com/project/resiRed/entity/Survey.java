package com.project.resiRed.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;


import java.util.List;

@Entity
@Data
@Table(name="surveys")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyId;
    private String topic;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<Question> questions;

    @ManyToOne
    @Nullable
    @JoinColumn(name="assembly_id")
    private Assembly assembly;
}
