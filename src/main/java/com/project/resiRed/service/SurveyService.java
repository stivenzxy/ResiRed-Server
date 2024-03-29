package com.project.resiRed.service;

import java.util.ArrayList;
import java.util.List;

import com.project.resiRed.entity.User;
import com.project.resiRed.enums.UserRole;
import com.project.resiRed.dto.SurveyDto;
import com.project.resiRed.dto.QuestionDto;

import com.project.resiRed.repository.SurveyRepository;
import com.project.resiRed.repository.QuestionRepository;
import com.project.resiRed.repository.ChoiceRepository;
import com.project.resiRed.repository.UserRepository;

import com.project.resiRed.entity.Survey;
import com.project.resiRed.entity.Question;
import com.project.resiRed.entity.Choice;

import com.project.resiRed.controller.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final UserRepository userRepository;

    public ApiResponse createSurvey(SurveyDto surveyDto, String email) {

        Survey survey = new Survey();
        survey.setTopic(surveyDto.getTopic());
        User user = userRepository.findUserByEmail(email).get();
        survey.setUser(user);
        surveyRepository.save(survey);

        for (QuestionDto questionDto : surveyDto.getQuestions()) {
            Question question = new Question();
            question.setDescription(questionDto.getDescription());
            question.setSurvey(survey);
            questionRepository.save(question);
                for (String choiceStr : questionDto.getChoices()) {
                    Choice choice = new Choice();
                    choice.setDescription(choiceStr);
                    choice.setQuestion(question);
                    choice.setVotes(0);
                    choiceRepository.save(choice);
                }
        }
        return ApiResponse.builder().detail("Survey created").build();
    }

}
