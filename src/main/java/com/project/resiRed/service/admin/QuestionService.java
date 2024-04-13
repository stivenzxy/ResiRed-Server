package com.project.resiRed.service.admin;

import com.project.resiRed.dto.ChoiceDto.createChoiceRequest;
import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.dto.QuestionDto.updateQuestionRequest;

public interface QuestionService {
    MessageDto updateSurveyQuestion(Long questionId, updateQuestionRequest request);

    MessageDto addChoiceToQuestion(Long questionId, createChoiceRequest request);

    MessageDto deleteQuestion(Long questionId);

    MessageDto deleteChoice(Long choiceId);
}
