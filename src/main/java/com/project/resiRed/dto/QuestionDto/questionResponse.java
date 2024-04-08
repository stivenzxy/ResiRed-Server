package com.project.resiRed.dto.QuestionDto;

import com.project.resiRed.dto.ChoiceDto.choiceInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class questionResponse {
    private Long questionId;
    private String description;
    private List<choiceInfoResponse> choices;
}
