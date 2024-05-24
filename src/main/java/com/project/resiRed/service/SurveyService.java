package com.project.resiRed.service;
import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.dto.QuestionDto.questionResult;
import com.project.resiRed.dto.SurveyDto.*;

import com.project.resiRed.dto.QuestionDto.createQuestionRequest;
import com.project.resiRed.dto.QuestionDto.questionResponse;

import java.util.List;

public interface SurveyService {
    MessageDto createSurvey(createSurveyRequest request);
    List<SurveysListResponse> getAlLEditableSurveys();

    List<questionResponse> getSurveyQuestions(Long surveyId);

    MessageDto updateSurveyTopic(Long surveyId, updateTopicRequest request);

    MessageDto deleteSurvey(Long surveyId);

    questionResponse addQuestionToSurvey(Long surveyId, createQuestionRequest request);

    List<surveysOverviewResponse> getSurveysOverview(surveysOverviewRequest request);


    List<SurveyResponse> getAllAssemblySurveys(Long assemblyId);

    List<questionResult> getSurveyResults(Long surveyId);


}
