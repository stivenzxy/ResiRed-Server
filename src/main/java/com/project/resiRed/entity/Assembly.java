package com.project.resiRed.entity;

import com.project.resiRed.dto.AssemblyDto.createAssemblyRequest;
import jakarta.persistence.*;
import lombok.Data;

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
    private LocalTime time;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "assembly", cascade = CascadeType.ALL)
    private List<Survey> surveys;

    @ManyToMany
    @JoinTable(name = "attendance",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "assembly_id")
    )
    private List<User> users;

    public createAssemblyRequest getDto(){
        createAssemblyRequest createAssemblyRequest =new createAssemblyRequest();
        createAssemblyRequest.setTitle(title);
        createAssemblyRequest.setDescription(description);
        createAssemblyRequest.setDate(date);
        createAssemblyRequest.setTime(time);
        List<Long> surveyIds=new ArrayList<>();
        for(Survey survey:surveys){
            surveyIds.add(survey.getSurveyId());
        }
        createAssemblyRequest.setSurveys(surveyIds);
        return createAssemblyRequest;

    }

}
