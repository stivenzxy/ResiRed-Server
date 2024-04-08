package com.project.resiRed.controller;


import com.project.resiRed.entity.Survey;
import com.project.resiRed.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.dto.SurveyDto.createSurveyRequest;
import com.project.resiRed.dto.SurveyDto.updateTopicRequest;
import com.project.resiRed.dto.QuestionDto.updateQuestionRequest;
import com.project.resiRed.dto.QuestionDto.createQuestionRequest;
import com.project.resiRed.dto.ChoiceDto.createChoiceRequest;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.HttpStatus;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class surveyController {
    private final SurveyService surveyService;

    @PostMapping(value = "create")
    public  ResponseEntity<MessageDto> createSurvey(@RequestBody createSurveyRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ADMIN"))) {
            return ResponseEntity.ok(
                    surveyService.createSurvey(request)
            );
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    MessageDto.builder().detail("Insufficient permissions").build());
        }

    }


    @GetMapping(value = "list/unassigned")
    public  ResponseEntity<?> getAllUnassignedSurveys(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ADMIN"))) {
            return ResponseEntity.ok(
                    surveyService.getAllUnassignedSurveys()
            );
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    MessageDto.builder().detail("Insufficient permissions").build());
        }

    }

    @GetMapping(value = "{id}/list/questions")
    public  ResponseEntity<?> getSurveyQuestions(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ADMIN"))) {
            return ResponseEntity.ok(
                    surveyService.getSurveyQuestions(id)
            );
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    MessageDto.builder().detail("Insufficient permissions").build());
        }

    }

    @PutMapping(value = "{id}/update/topic")
    public  ResponseEntity<MessageDto> updateSurveyTopic(@PathVariable Long id, @RequestBody  updateTopicRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ADMIN"))) {
            return ResponseEntity.ok(
                    surveyService.updateSurveyTopic(id, request)
            );
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    MessageDto.builder().detail("Insufficient permissions").build());
        }

    }

    @DeleteMapping(value = "{id}/delete")
    public  ResponseEntity<MessageDto> deleteSurvey(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ADMIN"))) {
            return ResponseEntity.ok(
                    surveyService.deleteSurvey(id)
            );
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    MessageDto.builder().detail("Insufficient permissions").build());
        }

    }

    @PostMapping(value = "{id}/add/question")
    public  ResponseEntity<MessageDto> addQuestiontoSurvey(@PathVariable Long id, @RequestBody createQuestionRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ADMIN"))) {
            return ResponseEntity.ok(
                    surveyService.addQuestiontoSurvey(id, request)
            );
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    MessageDto.builder().detail("Insufficient permissions").build());
        }

    }

}
