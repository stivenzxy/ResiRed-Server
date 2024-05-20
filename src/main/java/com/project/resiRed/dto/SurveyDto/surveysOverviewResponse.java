package com.project.resiRed.dto.SurveyDto;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class surveysOverviewResponse {
    private String topic;
    private List<questionOverviewResponse> questions;

}
