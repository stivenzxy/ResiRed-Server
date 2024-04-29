package com.project.resiRed.dto;

import com.project.resiRed.entity.Survey;
import com.project.resiRed.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class AssemblyDto {
    Long assemblyId;
    String title;
    String description;
    LocalDate date;
    LocalTime time;
    List<Long> surveys;
    List<User> attendees;

}
