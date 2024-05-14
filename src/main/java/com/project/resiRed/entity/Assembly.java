package com.project.resiRed.entity;

import com.project.resiRed.dto.AssemblyDto.AssemblyResponse;
import com.project.resiRed.dto.AssemblyDto.SurveyOverview;
import com.project.resiRed.dto.AssemblyDto.createAssemblyRequest;
import com.project.resiRed.enums.AssemblyStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "assembly_id")
    )
    private List<User> users;

    public AssemblyResponse getDto() {
        AssemblyResponse assemblyResponse = new AssemblyResponse();
        assemblyResponse.setId(assemblyId);
        assemblyResponse.setTitle(title);
        assemblyResponse.setDescription(description);
        assemblyResponse.setDate(date);
        assemblyResponse.setStartTime(startTime);
        assemblyResponse.setStatus(status);

        // Creando la lista de SurveyOverview a partir de las encuestas
        List<SurveyOverview> surveyOverviews = new ArrayList<>();
        for (Survey survey : surveys) {
            SurveyOverview overview = new SurveyOverview();
            overview.setId(survey.getSurveyId());
            overview.setTopic(survey.getTopic()); // Asumiendo que Survey tiene un método getTopic()
            surveyOverviews.add(overview);
        }
        assemblyResponse.setSurveys(surveyOverviews);
        return assemblyResponse;
    }
}