package com.project.resiRed.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quetionId;
    private String description;
    @OneToMany
    private List<Choice> choices;
}
