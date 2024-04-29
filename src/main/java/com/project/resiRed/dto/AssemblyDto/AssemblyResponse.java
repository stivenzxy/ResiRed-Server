package com.project.resiRed.dto.AssemblyDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssemblyResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime startTime;
    private List<SurveyOverview> surveys;
}

