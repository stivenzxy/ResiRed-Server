package com.project.resiRed.dto.SurveyDto;

import com.project.resiRed.entity.Assembly;
import com.project.resiRed.entity.Question;
import com.project.resiRed.entity.Survey;
import com.project.resiRed.service.admin.AssemblyService;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SurveyDto {
    private Long surveyId;
    private String topic;
    private LocalDateTime createdAt;
    private List<Question> questions;
    private Assembly assembly;
    private AssemblyService assemblyService;

    public Survey toEntity() {
        Survey survey = new Survey();
        survey.setSurveyId(surveyId);
        survey.setTopic(topic);
        survey.setCreatedAt(createdAt);


        return survey;
    }
}
