package com.project.resiRed.dto.SurveyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SurveysResponse {
    private Long surveyId;
    private String dateCreated;
    private String topic;
}
