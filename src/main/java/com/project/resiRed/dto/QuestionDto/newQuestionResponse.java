package com.project.resiRed.dto.QuestionDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class newQuestionResponse {
    private Long questionId;
    private String detail;
}