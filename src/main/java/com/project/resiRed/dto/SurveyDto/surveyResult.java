package com.project.resiRed.dto.SurveyDto;


import com.project.resiRed.dto.QuestionDto.questionResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class surveyResult {
    private String topic;
    private List<questionResult> questions;
}