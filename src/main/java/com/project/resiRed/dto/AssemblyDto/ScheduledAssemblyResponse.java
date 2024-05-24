package com.project.resiRed.dto.AssemblyDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduledAssemblyResponse {
    private Boolean isScheduled;
    private Long assemblyId;
    private String title;
    private LocalDate date;
    private LocalTime startTime;
    private Boolean isAvailable;
}
