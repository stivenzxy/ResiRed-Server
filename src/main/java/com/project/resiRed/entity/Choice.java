package com.project.resiRed.entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.print.attribute.standard.QueuedJobCount;

@Entity
@Data
@Table(name = "choices")
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long choiceId;
    private String description;
    private int votes;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="question_id")
    private Question question;

}
