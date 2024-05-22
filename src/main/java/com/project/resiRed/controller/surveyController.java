package com.project.resiRed.controller;



import com.project.resiRed.dto.QuestionDto.questionResult;
import com.project.resiRed.dto.SurveyDto.surveysOverviewRequest;
import com.project.resiRed.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.dto.SurveyDto.createSurveyRequest;
import com.project.resiRed.dto.SurveyDto.updateTopicRequest;
import com.project.resiRed.dto.SurveyDto.SurveysListResponse;
import com.project.resiRed.dto.QuestionDto.questionResponse;

import com.project.resiRed.dto.QuestionDto.createQuestionRequest;

import java.util.List;


@RestController
@RequestMapping("api/admin/survey")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class surveyController {
    private final SurveyService surveyService;

    @PostMapping(value = "create")
    public ResponseEntity<MessageDto> createSurvey(@RequestBody createSurveyRequest request) {
        return ResponseEntity.ok(surveyService.createSurvey(request));
    }


    @GetMapping(value = "list/edit")

    public  ResponseEntity<List<SurveysListResponse>> getAlLSurveys(){
            return ResponseEntity.ok(surveyService.getAlLEditableSurveys());
    }

    @GetMapping(value = "{id}/list/questions")
    public ResponseEntity<List<questionResponse>> getSurveyQuestions(@PathVariable Long id) {
        return ResponseEntity.ok(surveyService.getSurveyQuestions(id));
    }

    @PutMapping(value = "{id}/update/topic")
    public ResponseEntity<MessageDto> updateSurveyTopic(@PathVariable Long id, @RequestBody updateTopicRequest request) {
        return ResponseEntity.ok(surveyService.updateSurveyTopic(id, request));
    }

    @DeleteMapping(value = "{id}/delete")
    public ResponseEntity<MessageDto> deleteSurvey(@PathVariable Long id) {
        return ResponseEntity.ok(surveyService.deleteSurvey(id));
    }

    @PostMapping(value = "{id}/add/question")

    public  ResponseEntity<questionResponse> addQuestionToSurvey(@PathVariable Long id, @RequestBody createQuestionRequest request){
            return ResponseEntity.ok(surveyService.addQuestionToSurvey(id, request));

    }

    @GetMapping("surveys/overview")
    public ResponseEntity<?> getSurveysOverview(@RequestBody surveysOverviewRequest request){
        return ResponseEntity.ok(surveyService.getSurveysOverview(request));
    }

    @GetMapping(value = "list/{assemblyId}")
    public ResponseEntity<?> getAllAssemblySurveys(@PathVariable Long assemblyId){
        return ResponseEntity.ok(surveyService.getAllAssemblySurveys(assemblyId));
    }

    @GetMapping(value = "{id}/list/questions/results")
    public ResponseEntity<List<questionResult>> getSurveyResults(@PathVariable Long id) {
        return ResponseEntity.ok(surveyService.getSurveyResults(id));
    }
}


