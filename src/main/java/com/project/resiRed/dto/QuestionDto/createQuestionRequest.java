package com.project.resiRed.dto.QuestionDto;

import com.project.resiRed.dto.ChoiceDto.createChoiceRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class createQuestionRequest {
    private String description;
    private List<createChoiceRequest> choices;
}
