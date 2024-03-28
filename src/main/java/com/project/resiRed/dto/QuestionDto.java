package com.project.resiRed.dto;

import com.project.resiRed.dto.ChoiceDto;
import lombok.Data;

import java.util.List;

@Data
public class QuestionDto {
    private String description;
    private List<ChoiceDto> choices;
}
