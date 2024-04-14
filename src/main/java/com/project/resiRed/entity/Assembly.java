package com.project.resiRed.entity;

import com.project.resiRed.dto.AssemblyDto;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
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
    @ManyToMany(mappedBy = "assemblies")
    private Set<Attendance> attendances = new HashSet<>();


    @OneToMany(mappedBy = "assembly", cascade = CascadeType.ALL)
    private List<Survey> surveys;

    public AssemblyDto getDto(){
        AssemblyDto assemblyDto=new AssemblyDto();
        assemblyDto.setAssemblyId(assemblyId);
        assemblyDto.setTitle(title);
        assemblyDto.setDescription(description);
        assemblyDto.setDate(date);
        assemblyDto.setTime(time);
        List<Long> surveyIds=new ArrayList<>();
        for(Survey survey:surveys){
            surveyIds.add(survey.getSurveyId());
        }
        assemblyDto.setSurveys(surveyIds);
        return assemblyDto;

    }

}
