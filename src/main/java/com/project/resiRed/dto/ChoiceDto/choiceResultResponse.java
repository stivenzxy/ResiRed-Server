package com.project.resiRed.dto.ChoiceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class choiceResultResponse {
    private String description;
    private int votes;
}
