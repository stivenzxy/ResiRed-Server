package com.project.resiRed.entity;

import com.project.resiRed.dto.AssemblyDto.AssemblyResponse;
import com.project.resiRed.dto.SurveyDto.SurveysListResponse;
import com.project.resiRed.enums.AssemblyStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table (name="assemblies")
public class Assembly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assemblyId;
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime finishedTime;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private AssemblyStatus status;
    @OneToMany(mappedBy = "assembly", cascade = CascadeType.ALL)
    private List<Survey> surveys;

    @ManyToMany
    @JoinTable(name = "attendance",
            joinColumns = @JoinColumn(name = "assembly_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    public AssemblyResponse getDto() {
        AssemblyResponse assemblyResponse = new AssemblyResponse();
        assemblyResponse.setId(assemblyId);
        assemblyResponse.setTitle(title);
        assemblyResponse.setDescription(description);
        assemblyResponse.setDate(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        assemblyResponse.setStartTime(startTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        assemblyResponse.setStatus(status);

        List<SurveysListResponse> surveysList = new ArrayList<>();
            for (Survey survey : surveys) {
                surveysList.add(SurveysListResponse.builder()
                        .surveyId(survey.getSurveyId())
                        .dateCreated(survey.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .topic(survey.getTopic())
                        .build());
        }
        assemblyResponse.setSurveys(surveysList);
        return assemblyResponse;
    }
}