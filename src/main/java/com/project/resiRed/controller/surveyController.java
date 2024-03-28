package com.project.resiRed.controller;

import com.project.resiRed.service.SurveyService;
import com.project.resiRed.entity.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.resiRed.dto.SurveyDto;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class surveyController {
    private final SurveyService surveyService;

    @PostMapping(value = "create")
    public  ResponseEntity<Survey> createSurvey(@RequestBody SurveyDto surveyDto){
        return ResponseEntity.ok(surveyService.createSurvey(surveyDto));
    }
}
