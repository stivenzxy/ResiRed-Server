package com.project.resiRed.dto;

import com.project.resiRed.dto.QuestionDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SurveyDto {
    private String topic;
    private List<QuestionDto> questions;
}