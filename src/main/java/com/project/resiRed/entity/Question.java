package com.project.resiRed.entity;

import com.project.resiRed.dto.ChoiceDto.choiceResponse;
import com.project.resiRed.dto.QuestionDto.questionResponse;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;
    private String description;
    private Boolean canBeVoted;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="survey_id")
    private Survey survey;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Choice> choices;

    public questionResponse getDto(){
        List<choiceResponse> choicesList = new ArrayList<choiceResponse>();
        for (Choice choice : choices) {
            choicesList.add(choiceResponse.builder()

                    .choiceId(choice.getChoiceId())
                    .description(choice.getDescription()).build());
        }
        return questionResponse.builder()
                .questionId(questionId)
                .description(description)
                .choices(choicesList)
                .build();
    }
}
