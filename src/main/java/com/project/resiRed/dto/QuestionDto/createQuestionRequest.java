package com.project.resiRed.dto.QuestionDto;
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
    private List<String> choices;
}
