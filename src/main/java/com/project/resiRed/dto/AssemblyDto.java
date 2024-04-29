package com.project.resiRed.dto;

import com.project.resiRed.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class AssemblyDto {
    private Long assemblyId;
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private List<Long> surveys;
    private List<User> attendees = new ArrayList<>();
}
