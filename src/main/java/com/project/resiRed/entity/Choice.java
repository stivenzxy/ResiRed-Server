package com.project.resiRed.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "choices")
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long choiceId;
    private String decription;
    private int votes;
}
