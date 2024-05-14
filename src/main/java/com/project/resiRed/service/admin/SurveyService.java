package com.project.resiRed.service.admin;
import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.dto.SurveyDto.SurveyResponse;
import com.project.resiRed.dto.SurveyDto.createSurveyRequest;

import com.project.resiRed.dto.SurveyDto.updateTopicRequest;
import com.project.resiRed.dto.SurveyDto.SurveysListResponse;
import com.project.resiRed.dto.QuestionDto.createQuestionRequest;
import com.project.resiRed.dto.QuestionDto.questionResponse;

import java.util.List;

public interface SurveyService {
    MessageDto createSurvey(createSurveyRequest request);
    List<SurveysListResponse> getAlLEditableSurveys();

    List<questionResponse> getSurveyQuestions(Long surveyId);

    MessageDto updateSurveyTopic(Long surveyId, updateTopicRequest request);

    MessageDto deleteSurvey(Long surveyId);

    questionResponse addQuestiontoSurvey(Long surveyId, createQuestionRequest request);


    List<SurveyResponse> getAllAssemblySurveys();

}
