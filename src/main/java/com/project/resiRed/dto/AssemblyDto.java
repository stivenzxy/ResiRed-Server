package com.project.resiRed.dto;

import com.project.resiRed.entity.Survey;
import com.project.resiRed.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
