package com.project.resiRed.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AssemblyDto {
    private Long assemblyId;
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime time;
}
