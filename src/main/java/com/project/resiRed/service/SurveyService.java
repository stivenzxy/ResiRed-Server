package com.project.resiRed.service;

import com.project.resiRed.entity.User;
import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.dto.SurveyDto.SurveyRequestDto;
import com.project.resiRed.dto.QuestionDto;

import com.project.resiRed.repository.SurveyRepository;
import com.project.resiRed.repository.QuestionRepository;
import com.project.resiRed.repository.ChoiceRepository;
import com.project.resiRed.repository.UserRepository;

import com.project.resiRed.entity.Survey;
import com.project.resiRed.entity.Question;
import com.project.resiRed.entity.Choice;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final UserRepository userRepository;

    public MessageDto createSurvey(SurveyRequestDto surveyRequestDto, String email) {

        Survey survey = new Survey();
        survey.setTopic(surveyRequestDto.getTopic());
        User user = userRepository.findUserByEmail(email).get();
        survey.setUser(user);
        survey.setQuestions(new ArrayList<>());

        for (QuestionDto questionDto : surveyRequestDto.getQuestions()) {
            Question question = new Question();
            question.setDescription(questionDto.getDescription());
            question.setSurvey(survey);
            question.setChoices(new ArrayList<>());
            for (String choiceStr : questionDto.getChoices()) {
                Choice choice = new Choice();
                choice.setDescription(choiceStr);
                choice.setQuestion(question);
                choice.setVotes(0);
                question.getChoices().add(choice);
            }
            survey.getQuestions().add(question);
        }

        surveyRepository.save(survey);

        return MessageDto.builder().detail("Survey created").build();
    }

}