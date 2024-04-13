package com.project.resiRed.service.admin;
import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.dto.SurveyDto.createSurveyRequest;
import com.project.resiRed.dto.SurveyDto.updateTopicRequest;
import com.project.resiRed.dto.SurveyDto.unassignedSurveysResponse;
import com.project.resiRed.dto.QuestionDto.createQuestionRequest;
import com.project.resiRed.dto.QuestionDto.updateQuestionRequest;
import com.project.resiRed.dto.QuestionDto.questionResponse;
import com.project.resiRed.dto.ChoiceDto.choiceInfoResponse;
import com.project.resiRed.dto.ChoiceDto.createChoiceRequest;

import java.util.List;

public interface SurveyService {
    MessageDto createSurvey(createSurveyRequest request);
    List<unassignedSurveysResponse> getAllUnassignedSurveys();

    List<questionResponse> getSurveyQuestions(Long surveyId);

    MessageDto updateSurveyTopic(Long surveyId, updateTopicRequest request);

    MessageDto deleteSurvey(Long surveyId);

    MessageDto addQuestiontoSurvey(Long surveyId, createQuestionRequest request);


}
