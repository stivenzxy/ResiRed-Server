package com.project.resiRed.entity;

import com.project.resiRed.dto.SurveyDto.SurveyDto;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;


import java.util.List;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="surveys")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyId;
    private String topic;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<Question> questions;

    @ManyToOne
    @Nullable
    @JoinColumn(name="assembly_id")
    private Assembly assembly;

    public SurveyDto getDto() {
        SurveyDto dto = new SurveyDto();
        dto.setSurveyId(surveyId);
        dto.setTopic(topic);
        dto.setCreatedAt(createdAt);
        dto.setQuestions(questions);  // Asumiendo que las preguntas también tienen su propio método getDto()
        if (assembly != null) {
            dto.setAssembly(assembly);  // Esta línea puede requerir un getDto() en Assembly si solo deseas enviar un DTO de Assembly
        }
        return dto;
    }
}
