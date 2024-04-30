package com.project.resiRed.dto.AssemblyDto;

import java.time.LocalDate;
import java.time.LocalTime;


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
public class AssemblyAvailabilityResponse {
    private Boolean isAvailable;
    private LocalDate date;
    private LocalTime startTime;
}
