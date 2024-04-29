package com.project.resiRed.entity;

import com.project.resiRed.dto.AssemblyDto;
import com.project.resiRed.dto.UserDto;
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
    @ManyToMany
    @JoinTable(
            name = "attendance",
            joinColumns = @JoinColumn(name = "assembly_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> attendees = new ArrayList<>();


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
