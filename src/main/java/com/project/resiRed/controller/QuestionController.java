package com.project.resiRed.controller;


import com.project.resiRed.dto.ChoiceDto.createChoiceRequest;
import com.project.resiRed.dto.ChoiceDto.newChoiceResponse;
import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.dto.QuestionDto.updateQuestionRequest;
import com.project.resiRed.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.project.resiRed.service.authentication.JwtService;

@RestController
@RequestMapping("api/admin/question")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class QuestionController {
    private final QuestionService questionService;
    private final JwtService jwtService;


    @PutMapping(value = "{id}/update")
    public ResponseEntity<MessageDto> updateSurveyQuestion(@PathVariable Long id, @RequestBody updateQuestionRequest request){
            return ResponseEntity.ok(questionService.updateSurveyQuestion(id, request));
    }

    @DeleteMapping(value = "{id}/delete")
    public ResponseEntity<MessageDto> deleteQuestion(@PathVariable Long id){
            return ResponseEntity.ok(questionService.deleteQuestion(id));
    }

    @DeleteMapping(value = "delete/choice/{id}")
    public ResponseEntity<MessageDto> deleteChoice(@PathVariable Long id){
            return ResponseEntity.ok(
                    questionService.deleteChoice(id)
            );
    }

    @PostMapping(value = "{id}/add/choice")
    public  ResponseEntity<newChoiceResponse> addChoiceToQuestion(@PathVariable Long id, @RequestBody createChoiceRequest request){
            return ResponseEntity.ok(questionService.addChoiceToQuestion(id, request));
    }

    @PutMapping(value = "set/current/{id}")
    public ResponseEntity<?> setCurrentQuestion(@PathVariable Long id){
        return ResponseEntity.ok(questionService.setCurrentQuestion(id));
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping(value = "get/current/{assemblyId}")
    public ResponseEntity<?> getCurrentQuestion(@PathVariable Long assemblyId){

        return ResponseEntity.ok(questionService.getCurrentQuestion(assemblyId));
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @PostMapping(value = "{questionId}/vote/{choiceId}")
    public ResponseEntity<?> voteQuestion(@PathVariable Long questionId, @PathVariable Long choiceId,@RequestHeader("Authorization") String authorizationHeader){
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        String email = jwtService.getEmailFromToken(jwtToken);
        return ResponseEntity.ok(questionService.voteQuestion(questionId, choiceId, email));
    }
}