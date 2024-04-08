package com.project.resiRed.service;

import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.dto.SurveyDto.createSurveyRequest;
import com.project.resiRed.dto.SurveyDto.updateTopicRequest;
import com.project.resiRed.dto.SurveyDto.unassignedSurveysResponse;
import com.project.resiRed.dto.QuestionDto.createQuestionRequest;
import com.project.resiRed.dto.QuestionDto.updateQuestionRequest;
import com.project.resiRed.dto.QuestionDto.questionResponse;
import com.project.resiRed.dto.ChoiceDto.choiceInfoResponse;
import com.project.resiRed.dto.ChoiceDto.createChoiceRequest;


import com.project.resiRed.repository.SurveyRepository;
import com.project.resiRed.repository.QuestionRepository;
import com.project.resiRed.repository.ChoiceRepository;
import com.project.resiRed.repository.UserRepository;

import com.project.resiRed.entity.Survey;
import com.project.resiRed.entity.Question;
import com.project.resiRed.entity.Choice;
import org.springframework.data.domain.Sort;


import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final UserRepository userRepository;

    public MessageDto createSurvey(createSurveyRequest request) {

        Survey survey = new Survey();
        survey.setCreatedAt(LocalDateTime.now());
        survey.setTopic(request.getTopic());
        survey.setQuestions(new ArrayList<>());

        for (createQuestionRequest questionDto : request.getQuestions()) {
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

    public List<unassignedSurveysResponse> getAllUnassignedSurveys() {
        List<Survey> allSurveys = surveyRepository.findAllAssemblyIsNull();
        List<unassignedSurveysResponse> response = new ArrayList<unassignedSurveysResponse>();
        for (Survey survey : allSurveys) {
            response.add(unassignedSurveysResponse.builder()
                    .surveyId(survey.getSurveyId())
                    .topic(survey.getTopic())
                    .build());
        }

        return response;

    }

    public List<questionResponse> getSurveyQuestions(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId).get();

        List<questionResponse> response = new ArrayList<questionResponse>();

        for (Question question : questionRepository.findAllBySurvey(survey)) {
            List<choiceInfoResponse> choices = new ArrayList<choiceInfoResponse>();
            for (Choice choice : choiceRepository.findAllByQuestion(question)) {
                choices.add(choiceInfoResponse.builder()
                        .choiceId(choice.getChoiceId())
                        .description(choice.getDescription()).build());
            }
            response.add(questionResponse.builder()
                    .questionId(question.getQuestionId())
                    .description(question.getDescription()).choices(choices).build()
            );
        }

        return response;
    }

   public MessageDto updateSurveyTopic(Long surveyId, updateTopicRequest request){
        Survey survey = surveyRepository.findById(surveyId).get();
       if(Objects.nonNull(request.getTopic()) && !"".equalsIgnoreCase(request.getTopic())){
           survey.setTopic(request.getTopic());
       }
       surveyRepository.save(survey);

       return MessageDto.builder().detail("Topic Name Updated").build();

   }

    public MessageDto deleteSurvey(Long surveyId){
            surveyRepository.deleteById(surveyId);
            return MessageDto.builder().detail("Survey Deleted").build();
    }



}
