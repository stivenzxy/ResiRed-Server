package com.project.resiRed.service.admin;
import com.project.resiRed.dto.ChoiceDto.choiceResponse;
import com.project.resiRed.dto.ChoiceDto.createChoiceRequest;
import com.project.resiRed.dto.ChoiceDto.newChoiceResponse;
import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.dto.QuestionDto.questionResponse;
import com.project.resiRed.dto.QuestionDto.updateQuestionRequest;
import com.project.resiRed.dto.SurveyDto.nextSurveyResponse;
import com.project.resiRed.entity.Choice;
import com.project.resiRed.entity.Question;
import com.project.resiRed.entity.Survey;
import com.project.resiRed.repository.QuestionRepository;
import com.project.resiRed.repository.ChoiceRepository;


import com.project.resiRed.repository.SurveyRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.*;


@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService{

    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final SurveyRepository surveyRepository;

    @Override
    public MessageDto updateSurveyQuestion(Long questionId, updateQuestionRequest request){
        Question question = questionRepository.findById(questionId).get();
        if(Objects.nonNull(request.getDescription()) && !"".equalsIgnoreCase(request.getDescription())){
            question.setDescription(request.getDescription());
        }
        if(Objects.nonNull(request.getChoices())) {
            for (choiceResponse choiceDto : request.getChoices()) {
                if (Objects.nonNull(choiceDto.getDescription()) && !"".equalsIgnoreCase(choiceDto.getDescription())) {
                    Choice choice = choiceRepository.findById(choiceDto.getChoiceId()).get();
                    choice.setDescription(choiceDto.getDescription());
                }
            }
        }

        questionRepository.save(question);

        return MessageDto.builder().detail("Question updated").build();
    }

    @Override
    public newChoiceResponse addChoiceToQuestion(Long questionId, createChoiceRequest request){
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NoSuchElementException("Question not found"));
        Choice choice = new Choice();
        choice.setDescription(request.getDescription());
        choice.setVotes(0);

        choiceRepository.saveAndFlush(choice); // Guardar y flushear antes de añadir a la colección

        choice.setQuestion(question);
        question.getChoices().add(choice);
        questionRepository.save(question); // Guardar la pregunta actualizada (preventivo)

        return new newChoiceResponse(choice.getChoiceId(), "Choice added to question");
    }

    @Override
    public MessageDto deleteQuestion(Long questionId){
        questionRepository.deleteById(questionId);
        return MessageDto.builder().detail("Question Deleted").build();
    }

    @Override
    public MessageDto deleteChoice(Long choiceId){
        choiceRepository.deleteById(choiceId);
        return MessageDto.builder().detail("Choice Deleted").build();
    }

    @Override
    public questionResponse getFirstQuestion() {

            Question question = questionRepository.findAll().stream().findFirst().get();
            question.setCanBeVoted(true);
            questionRepository.save(question);
            return question.getDto();
    }

    @Override
    public questionResponse getNextQuestion(Long surveyId) {

            List<Question> questions = questionRepository.findNextQuestion(surveyId);
            if (!questions.isEmpty()) {
                Optional<Question> addedQuestion = questionRepository.findByCanBeVoted(true);
                if (addedQuestion.isPresent()) {
                    return addedQuestion.get().getDto();
                } else {
                    Question question = questions.stream().findFirst().get();
                    question.setCanBeVoted(true);
                    questionRepository.save(question);
                    return question.getDto();
                }
            }

        return null;
    }



        @Override
        public questionResponse getCurrentQuestion(){
            Optional<Question> question = questionRepository.findByCanBeVoted(true);
            return question.map(Question::getDto).orElse(null);
        }

        @Override
        public nextSurveyResponse saveVoting(){
            Optional<Question> lastQuestion = questionRepository.findByCanBeVoted(true);
            if(lastQuestion.isPresent()){
                lastQuestion.get().setVoted(true);
                lastQuestion.get().setCanBeVoted(false);
                questionRepository.save(lastQuestion.get());
            }


            List<Long> surveys_id = surveyRepository.findNextSurvey();

            if(!surveys_id.isEmpty()){
            List<Question> questions = questionRepository.findBySurveyAndVoted(lastQuestion.get().getSurvey(), false);

            Survey survey = surveyRepository.findById(surveys_id.get(0)).get();
                return nextSurveyResponse.builder()
                        .nextId(surveys_id.get(0))
                        .nextTopic(survey.getTopic())
                        .isLastQuestion(questions.isEmpty())
                        .isLastSurvey(false)
                        .build();

            }
            return nextSurveyResponse.builder()
                    .isLastSurvey(true)
                    .build();


        }






}
