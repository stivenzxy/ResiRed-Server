package com.project.resiRed.service;

import java.util.ArrayList;
import java.util.List;

import com.project.resiRed.enums.UserRole;
import com.project.resiRed.dto.SurveyDto;
import com.project.resiRed.dto.QuestionDto;
import com.project.resiRed.dto.ChoiceDto;
import com.project.resiRed.repository.SurveyRepository;
import com.project.resiRed.repository.QuestionRepository;
import com.project.resiRed.repository.ChoiceRepository;

import com.project.resiRed.entity.Survey;
import com.project.resiRed.entity.Question;
import com.project.resiRed.entity.Choice;



import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;

    public Survey createSurvey(SurveyDto surveyDto) {

        Survey survey = new Survey();
        survey.setTopic(surveyDto.getTopic());

        List<Question> questions = new ArrayList<>();

        for (QuestionDto questionDto : surveyDto.getQuestions()) {
            Question question = new Question();
            question.setDescription(questionDto.getDescription());
            question.setSurvey(survey);
            questionRepository.save(question);
                for (ChoiceDto choiceDto : questionDto.getChoices()) {
                    Choice choice = new Choice();
                    choice.setDescription(choiceDto.getDescription());
                    choice.setQuestion(question);
                    choiceRepository.save(choice);
                }

            questions.add(question);
        }

        survey.setQuestions(questions);
        surveyRepository.save(survey);

        return survey;
    }

}
