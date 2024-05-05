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
public class currentSurveyResponse {
    private Long id;
    private boolean isLastQuestion;
    private boolean isLastSurvey;
}
