package com.project.resiRed.service.admin;

import com.project.resiRed.dto.ChoiceDto.createChoiceRequest;
import com.project.resiRed.dto.ChoiceDto.newChoiceResponse;
import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.dto.QuestionDto.questionResponse;
import com.project.resiRed.dto.QuestionDto.updateQuestionRequest;
import com.project.resiRed.dto.SurveyDto.nextSurveyResponse;

public interface QuestionService {
    MessageDto updateSurveyQuestion(Long questionId, updateQuestionRequest request);

    newChoiceResponse addChoiceToQuestion(Long questionId, createChoiceRequest request);

    MessageDto deleteQuestion(Long questionId);

    MessageDto deleteChoice(Long choiceId);

    questionResponse getFirstQuestion();


    questionResponse getNextQuestion(Long surveyId);

    questionResponse getCurrentQuestion();

    nextSurveyResponse saveVoting();


}
