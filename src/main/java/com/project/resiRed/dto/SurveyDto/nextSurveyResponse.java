package com.project.resiRed.dto.SurveyDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class nextSurveyResponse {
    private Long nextId;
    private String nextTopic;
    private boolean isLastQuestion;
    private boolean isLastSurvey;
}
