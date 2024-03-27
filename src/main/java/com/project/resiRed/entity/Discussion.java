package com.project.resiRed.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
@Table(name = "discussion")
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discussionId;
    @ManyToOne
    private Assembly assembly;

    @OneToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;


}
