package com.project.resiRed.entity;

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
    @OneToMany(mappedBy = "survey")
    private List<Question> questions;
    @OneToOne(mappedBy = "survey")
    private Discussion discussion;
}
