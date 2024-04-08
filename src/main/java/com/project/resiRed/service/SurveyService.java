package com.project.resiRed.service;

import com.project.resiRed.entity.User;
import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.dto.SurveyDto;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final UserRepository userRepository;

    public MessageDto createSurvey(SurveyDto surveyDto) {

        Survey survey = new Survey();
        survey.setCreatedAt(LocalDateTime.now());
        survey.setTopic(surveyDto.getTopic());
        survey.setQuestions(new ArrayList<>());

        for (QuestionDto questionDto : surveyDto.getQuestions()) {
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

    public List<SurveyDto> getAllSurveys(){
        List<Survey> allSurveys = surveyRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        List<SurveyDto> surveyDtos = new ArrayList<SurveyDto>();
        for (Survey survey : allSurveys) {
            List<QuestionDto> questionDtos = new ArrayList<QuestionDto>();
            for(Question question : questionRepository.findAllBySurvey(survey)){
                List<String> choices = new ArrayList<String>();
                for(Choice choice : choiceRepository.findAllByQuestion(question)){
                    choices.add(choice.getDescription());
                }
                questionDtos.add(QuestionDto.builder()
                        .description(question.getDescription()).choices(choices).build()
                );
            }
            surveyDtos.add(SurveyDto.builder()
                    .topic(survey.getTopic())
                    .questions(questionDtos).build());

        }

        return surveyDtos;
    }



}
