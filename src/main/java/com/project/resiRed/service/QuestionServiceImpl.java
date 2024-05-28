package com.project.resiRed.service;
import com.project.resiRed.dto.ChoiceDto.choiceResponse;
import com.project.resiRed.dto.ChoiceDto.createChoiceRequest;
import com.project.resiRed.dto.ChoiceDto.newChoiceResponse;
import com.project.resiRed.dto.MessageDto;

import com.project.resiRed.dto.QuestionDto.currentQuestionResponse;

import com.project.resiRed.dto.QuestionDto.updateQuestionRequest;
import com.project.resiRed.entity.Assembly;
import com.project.resiRed.entity.Choice;
import com.project.resiRed.entity.Question;
import com.project.resiRed.entity.User;
import com.project.resiRed.enums.AssemblyStatus;
import com.project.resiRed.repository.*;


import com.project.resiRed.service.QuestionService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.*;


@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final AssemblyRepository assemblyRepository;
    private final UserRepository userRepository;

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
    public MessageDto setCurrentQuestion(Long questionId){
        Optional<Question> lastQuestion = questionRepository.findByCanBeVoted(true);
        lastQuestion.ifPresent(question -> question.setCanBeVoted(false));

        Question question = questionRepository.findById(questionId).get();
        question.setCanBeVoted(true);
        questionRepository.save(question);

        return MessageDto.builder()
                .detail("Question Set to Vote")
                .build();
    }

    @Override
    public currentQuestionResponse getCurrentQuestion(Long assemblyId){
        Assembly assembly = assemblyRepository.findById(assemblyId).get();

        Optional<Question> currentQuestion = questionRepository.findByCanBeVoted(true);
        if(currentQuestion.isPresent()){
            List<choiceResponse> choices = new ArrayList<choiceResponse>();
            for(Choice choice : currentQuestion.get().getChoices()){
                choices.add(choiceResponse.builder()
                        .choiceId(choice.getChoiceId())
                        .description(choice.getDescription())
                        .build());
            }
            return currentQuestionResponse.builder()
                    .topic(currentQuestion.get().getSurvey().getTopic())
                    .questionId(currentQuestion.get().getQuestionId())
                    .description(currentQuestion.get().getDescription())
                    .choices(choices)
                    .build();
        }

        return null;

    }

    @Override
    public boolean isQuestionAlreadyVoted(Long questionId, Long userId){
        return questionRepository.findByQuestionIdAndUserId(questionId, userId).isPresent();
    }


    @Override
    @Transactional
    public MessageDto voteQuestion(Long questionId, Long choiceId, String email) {

        User user = userRepository.findUserByEmail(email).get();

        if(isQuestionAlreadyVoted(questionId, user.getUserId())){
            return MessageDto.builder().detail("No puedes votar dos veces sobre la misma pregunta").build();
        }

        Question question = questionRepository.findById(questionId).get();
        Choice choice = choiceRepository.findById(choiceId).get();
        choice.setVotes(choice.getVotes() + 1);

        question.getUsers().add(user);

        questionRepository.save(question);


        return MessageDto.builder().detail("Voto guardado exitosamente").build();
    }






}
