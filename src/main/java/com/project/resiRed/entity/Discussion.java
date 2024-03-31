package com.project.resiRed.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "discussion")
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discussionId;

    @ManyToOne
    @JoinColumn(name = "assembly_id")
    private Assembly assembly;

    @OneToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;


}
