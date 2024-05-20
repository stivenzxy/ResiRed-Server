package com.project.resiRed.controller;

import com.project.resiRed.dto.AssemblyDto.*;
import com.project.resiRed.dto.SurveyDto.surveysOverviewRequest;
import com.project.resiRed.service.authentication.JwtService;
import org.springframework.http.ResponseEntity;
import com.project.resiRed.service.AssemblyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RequiredArgsConstructor
@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("api/admin/assembly")
public class AssemblyController {
    private final AssemblyService assemblyService;
    private final JwtService jwtService;


    @PostMapping("create")
    public ResponseEntity<?> createAssembly(@RequestBody createAssemblyRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                assemblyService.createAssembly(request)
        );
    }

    @GetMapping("surveys/overview")
    public ResponseEntity<?> getSurveysOverview(@RequestBody surveysOverviewRequest request){
        return ResponseEntity.ok(assemblyService.getSurveysOverview(request));
    }

    @DeleteMapping("delete/{assemblyId}")
    public  ResponseEntity<?> deleteAssembly(@PathVariable Long assemblyId){
        return ResponseEntity.ok(assemblyService.deleteAssembly(assemblyId));
    }

    @GetMapping("history")
    public ResponseEntity<List<AssemblyResponse>> getAllAssembliesHistory() {
        return ResponseEntity.ok(assemblyService.getAllAssembliesHistory());
    }


    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('OWNER')")
    @GetMapping("check/scheduled")
        public ResponseEntity<?> checkScheduledAssembly(){
        return ResponseEntity.ok(assemblyService.checkScheduledAssembly());
    }


    @PutMapping("cancel/scheduled")
    public ResponseEntity<?> cancelScheduledAssembly(){
        return ResponseEntity.ok(assemblyService.cancelScheduledAssembly());
    }

    @PutMapping("finish/started")
    public ResponseEntity<?> finishAssembly(){
        return ResponseEntity.ok(assemblyService.finishAssembly());
    }


    // Esto luego se quita
    @PreAuthorize("hasAuthority('OWNER')")
    @PostMapping("add/attendee")
    public ResponseEntity<Void> addAttendee(@RequestHeader("Authorization") String authorizationHeader){
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        String email = jwtService.getEmailFromToken(jwtToken);
        assemblyService.addAttendee(email);
        return ResponseEntity.ok().build();
    }



}
