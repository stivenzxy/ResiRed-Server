package com.project.resiRed.dto.ChoiceDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class choiceResult {
    private String description;
    private int votes;
}
