package com.project.resiRed.dto.QuestionDto;
import com.project.resiRed.dto.ChoiceDto.choiceResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class currentQuestionResponse {
    private String topic;
    private Long questionId;
    private String description;
    private List<choiceResponse> choices;
}
