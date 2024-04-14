package com.project.resiRed.dto;

import lombok.Data;

import java.util.List;

@Data
public class AttendanceDto {
    Long attendaceId;
    List<Long> userId;
    List<Long> assemblyId;
}
