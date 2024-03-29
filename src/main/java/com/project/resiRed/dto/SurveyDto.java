package com.project.resiRed.dto;

import com.project.resiRed.dto.QuestionDto;
import lombok.Data;

import java.util.List;

@Data
public class SurveyDto {
    private String topic;
    private List<QuestionDto> questions;
}
