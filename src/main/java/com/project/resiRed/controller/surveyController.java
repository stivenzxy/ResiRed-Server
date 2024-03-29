package com.project.resiRed.controller;


import com.project.resiRed.service.SurveyService;
import com.project.resiRed.entity.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.resiRed.dto.SurveyDto;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.HttpStatus;



@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class surveyController {
    private final SurveyService surveyService;

    @PostMapping(value = "create")
    public  ResponseEntity<Survey> createSurvey(@RequestBody SurveyDto surveyDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if user is authenticated (optional)
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Extract user details
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        return ResponseEntity.ok(surveyService.createSurvey(surveyDto, email));
    }
}
