package com.project.resiRed.service;
import com.project.resiRed.dto.ChoiceDto.choiceInfoResponse;
import com.project.resiRed.dto.ChoiceDto.createChoiceRequest;
import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.dto.QuestionDto.updateQuestionRequest;
import com.project.resiRed.entity.Choice;
import com.project.resiRed.entity.Question;
import com.project.resiRed.repository.QuestionRepository;
import com.project.resiRed.repository.ChoiceRepository;



import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    public MessageDto updateSurveyQuestion(Long questionId, updateQuestionRequest request){
        Question question = questionRepository.findById(questionId).get();
        if(Objects.nonNull(request.getDescription()) && !"".equalsIgnoreCase(request.getDescription())){
            question.setDescription(request.getDescription());
        }
        if(Objects.nonNull(request.getChoices())) {
            for (choiceInfoResponse choiceDto : request.getChoices()) {
                if (Objects.nonNull(choiceDto.getDescription()) && !"".equalsIgnoreCase(choiceDto.getDescription())) {
                    Choice choice = choiceRepository.findById(choiceDto.getChoiceId()).get();
                    choice.setDescription(choiceDto.getDescription());
                }
            }
        }

        questionRepository.save(question);

        return MessageDto.builder().detail("Survey updated").build();
    }

    public MessageDto deleteQuestion(Long questionId){
        questionRepository.deleteById(questionId);
        return MessageDto.builder().detail("Question Deleted").build();
    }

    public MessageDto addChoiceToQuestion(Long questionId, createChoiceRequest request){
        Question question = questionRepository.findById(questionId).get();
        Choice choice = new Choice();
        choice.setDescription(request.getDescription());
        choice.setQuestion(question);
        choice.setVotes(0);
        question.getChoices().add(choice);
        questionRepository.save(question);
        return MessageDto.builder().detail("Choice added to question").build();
    }
}
