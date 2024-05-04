package com.project.resiRed.controller.admin;


import com.project.resiRed.dto.QuestionDto.newQuestionResponse;
import com.project.resiRed.service.admin.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.dto.SurveyDto.createSurveyRequest;
import com.project.resiRed.dto.SurveyDto.updateTopicRequest;
import com.project.resiRed.dto.SurveyDto.unassignedSurveysResponse;
import com.project.resiRed.dto.QuestionDto.questionResponse;

import com.project.resiRed.dto.QuestionDto.createQuestionRequest;

import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.HttpStatus;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@RestController
@RequestMapping("api/admin/survey")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class surveyController {
    private final SurveyService surveyService;

    @PostMapping(value = "create")
    public  ResponseEntity<MessageDto> createSurvey(@RequestBody createSurveyRequest request){
            return ResponseEntity.ok(surveyService.createSurvey(request));
    }


    @GetMapping(value = "list/edit")
    public  ResponseEntity<List<unassignedSurveysResponse>> getAlLSurveys(){
            return ResponseEntity.ok(surveyService.getAlLEditableSurveys());
    }

    @GetMapping(value = "{id}/list/questions")
    public  ResponseEntity<List<questionResponse>> getSurveyQuestions(@PathVariable Long id){
            return ResponseEntity.ok(surveyService.getSurveyQuestions(id));
    }

    @PutMapping(value = "{id}/update/topic")
    public  ResponseEntity<MessageDto> updateSurveyTopic(@PathVariable Long id, @RequestBody  updateTopicRequest request){
            return ResponseEntity.ok(surveyService.updateSurveyTopic(id, request));
    }

    @DeleteMapping(value = "{id}/delete")
    public  ResponseEntity<MessageDto> deleteSurvey(@PathVariable Long id){
            return ResponseEntity.ok(surveyService.deleteSurvey(id));
    }

    @PostMapping(value = "{id}/add/question")
    public  ResponseEntity<newQuestionResponse> addQuestiontoSurvey(@PathVariable Long id, @RequestBody createQuestionRequest request){
            return ResponseEntity.ok(surveyService.addQuestiontoSurvey(id, request));

    }

}


/*

Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

 */
