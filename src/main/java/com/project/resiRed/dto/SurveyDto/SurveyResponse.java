package com.project.resiRed.dto.SurveyDto;

import com.project.resiRed.dto.QuestionDto.questionResponse;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SurveyResponse {
    private Long surveyId;
    private String topic;
    private List<questionResponse> questions;
}
