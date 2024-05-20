package com.project.resiRed.dto.QuestionDto;


import com.project.resiRed.dto.ChoiceDto.choiceResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class questionResult {
    private String description;
    private List<choiceResult> choices;
}
