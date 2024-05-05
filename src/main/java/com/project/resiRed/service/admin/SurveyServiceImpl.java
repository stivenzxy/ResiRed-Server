package com.project.resiRed.service.admin;

import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.dto.QuestionDto.newQuestionResponse;
import com.project.resiRed.dto.SurveyDto.createSurveyRequest;
import com.project.resiRed.dto.SurveyDto.currentSurveyResponse;
import com.project.resiRed.dto.SurveyDto.updateTopicRequest;
import com.project.resiRed.dto.SurveyDto.SurveysResponse;
import com.project.resiRed.dto.QuestionDto.createQuestionRequest;
import com.project.resiRed.dto.QuestionDto.questionResponse;
import com.project.resiRed.dto.ChoiceDto.choiceResponse;
import com.project.resiRed.dto.ChoiceDto.createChoiceRequest;


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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService{
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final UserRepository userRepository;

    @Override
    public MessageDto createSurvey(createSurveyRequest request) {

        Survey survey = new Survey();
        survey.setCreatedAt(LocalDateTime.now());
        survey.setTopic(request.getTopic());
        survey.setQuestions(new ArrayList<>());

        for (createQuestionRequest questionDto : request.getQuestions()) {
            Question question = new Question();
            question.setDescription(questionDto.getDescription());
            question.setVoted(false);
            question.setSurvey(survey);
            question.setChoices(new ArrayList<>());
            for (createChoiceRequest choiceDto : questionDto.getChoices()) {
                Choice choice = new Choice();
                choice.setDescription(choiceDto.getDescription());
                choice.setQuestion(question);
                choice.setVotes(0);
                question.getChoices().add(choice);
            }
            survey.getQuestions().add(question);
        }

        surveyRepository.save(survey);

        return MessageDto.builder().detail("Survey created").build();
    }

    @Override
    public List<SurveysResponse> getAlLEditableSurveys() {
        List<Survey> allSurveys = surveyRepository.findAllEditable();

        List<SurveysResponse> response = new ArrayList<SurveysResponse>();
        for (Survey survey : allSurveys) {
            response.add(SurveysResponse.builder()
                    .surveyId(survey.getSurveyId())
                    .dateCreated(survey.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .topic(survey.getTopic())
                    .build());
        }

        return response;

    }

    @Override
    public List<questionResponse> getSurveyQuestions(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId).get();

        List<questionResponse> response = new ArrayList<questionResponse>();

        for (Question question : questionRepository.findAllBySurvey(survey)) {
            List<choiceResponse> choices = new ArrayList<choiceResponse>();
            for (Choice choice : choiceRepository.findAllByQuestion(question)) {
                choices.add(choiceResponse.builder()
                        .choiceId(choice.getChoiceId())
                        .description(choice.getDescription()).build());
            }
            response.add(questionResponse.builder()
                    .questionId(question.getQuestionId())
                    .description(question.getDescription())
                    .choices(choices).build()
            );
        }

        return response;
    }

    @Override
   public MessageDto updateSurveyTopic(Long surveyId, updateTopicRequest request){
        Survey survey = surveyRepository.findById(surveyId).get();
       if(Objects.nonNull(request.getTopic()) && !"".equalsIgnoreCase(request.getTopic())){
           survey.setTopic(request.getTopic());
       }
       surveyRepository.save(survey);

       return MessageDto.builder().detail("Topic Name Updated").build();

   }

    @Override
    public MessageDto deleteSurvey(Long surveyId){
            surveyRepository.deleteById(surveyId);
            return MessageDto.builder().detail("Survey Deleted").build();
    }

    @Override
    public newQuestionResponse addQuestiontoSurvey(Long surveyId, createQuestionRequest request) {
        Survey survey = surveyRepository.findById(surveyId).get();
        Question question = new Question();
        question.setDescription(request.getDescription());
        question.setVoted(false);
        question.setSurvey(survey);
        question.setChoices(new ArrayList<>());
        for (createChoiceRequest choiceDto : request.getChoices()) {
            Choice choice = new Choice();
            choice.setDescription(choiceDto.getDescription());
            choice.setQuestion(question);
            choice.setVotes(0);
            question.getChoices().add(choice);
        }
        survey.getQuestions().add(question);

        questionRepository.saveAndFlush(question);
        surveyRepository.save(survey);

        return new newQuestionResponse(question.getQuestionId(), "Question added to Survey");
    }


    @Override
    public currentSurveyResponse checkNextSurvey(){
        List<Long> survey_id = surveyRepository.findCurrentSurvey();

        if(!survey_id.isEmpty()){
            return currentSurveyResponse.builder()
                    .id(survey_id.get(0))
                    .isLastQuestion(Collections.frequency(survey_id, survey_id.get(0)) == 1)
                    .isLastSurvey(false)
                    .build();
        }
        return currentSurveyResponse.builder()
                .id(null)
                .isLastQuestion(true)
                .isLastSurvey(true)
                .build();
    }
}
