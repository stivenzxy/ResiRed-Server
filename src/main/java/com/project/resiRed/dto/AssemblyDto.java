package com.project.resiRed.dto;

import com.project.resiRed.dto.SurveyDto.SurveyDto;
import com.project.resiRed.entity.Assembly;
import com.project.resiRed.entity.Survey;
import com.project.resiRed.entity.User;
import com.project.resiRed.service.admin.SurveyService;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AssemblyDto {
    private Long assemblyId;
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private List<Long> surveys;
    private List<User> attendees = new ArrayList<>();

    // Se asume la existencia de un servicio que puede recuperar las encuestas por ID
    private SurveyService surveyService; // Injectar este servicio

    public Assembly toEntity() {
        Assembly assembly = new Assembly();
        assembly.setAssemblyId(assemblyId);
        assembly.setTitle(title);
        assembly.setDescription(description);
        assembly.setDate(date);
        assembly.setTime(time);



        // Los asistentes son asignados directamente, asumiendo que la lista contiene entidades User completas
        assembly.setAttendees(new ArrayList<>(attendees));

        return assembly;
    }
}
