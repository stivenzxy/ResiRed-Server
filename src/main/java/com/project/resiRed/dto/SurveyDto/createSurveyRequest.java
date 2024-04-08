package com.project.resiRed.dto.SurveyDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import com.project.resiRed.dto.QuestionDto.createQuestionRequest;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class createSurveyRequest {
    private String topic;
    private List<createQuestionRequest> questions;
}

