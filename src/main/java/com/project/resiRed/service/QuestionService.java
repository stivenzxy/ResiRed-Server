package com.project.resiRed.service;

import com.project.resiRed.dto.ChoiceDto.createChoiceRequest;
import com.project.resiRed.dto.ChoiceDto.newChoiceResponse;
import com.project.resiRed.dto.MessageDto;

import com.project.resiRed.dto.QuestionDto.currentQuestionResponse;
import com.project.resiRed.dto.QuestionDto.updateQuestionRequest;
import com.project.resiRed.entity.Question;
import com.project.resiRed.entity.User;
import org.springframework.transaction.annotation.Transactional;

public interface QuestionService {
    MessageDto updateSurveyQuestion(Long questionId, updateQuestionRequest request);

    newChoiceResponse addChoiceToQuestion(Long questionId, createChoiceRequest request);

    MessageDto deleteQuestion(Long questionId);

    MessageDto deleteChoice(Long choiceId);


    MessageDto setCurrentQuestion(Long questionId);

    currentQuestionResponse getCurrentQuestion();

    boolean isQuestionAlreadyVoted(Long questionId, Long userId);
    MessageDto voteQuestion(Long questionId, Long choiceId, String email);

}
