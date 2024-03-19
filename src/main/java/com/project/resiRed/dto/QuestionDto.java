package com.project.resiRed.dto;

import com.project.resiRed.entity.Choice;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class QuestionDto {
    private Long quetionId;
    private String description;
    private List<Choice> choices;
}
