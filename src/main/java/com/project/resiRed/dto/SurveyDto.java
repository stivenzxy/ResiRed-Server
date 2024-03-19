package com.project.resiRed.dto;

import com.project.resiRed.entity.Question;
import lombok.Data;

import java.util.List;

@Data
public class SurveyDto {
    private Long surveyId;
    private String topic;
    private List<Question> questions;
}
