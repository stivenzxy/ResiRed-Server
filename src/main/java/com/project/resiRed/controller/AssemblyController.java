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

    @GetMapping("generate/passcode/{assemblyId}")
    public ResponseEntity<?> generateCode(@PathVariable Long assemblyId){
        return ResponseEntity.ok(assemblyService.generateCode(assemblyId));
    }


    @PutMapping("cancel/{assemblyId}")
    public ResponseEntity<?> cancelAssembly(@PathVariable Long assemblyId){
        return ResponseEntity.ok(assemblyService.cancelAssembly(assemblyId));
    }

    @PutMapping("finish/{assemblyId}")
    public ResponseEntity<?> finishAssembly(@PathVariable Long assemblyId){
        return ResponseEntity.ok(assemblyService.finishAssembly(assemblyId));
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("status/{assemblyId}")
    public ResponseEntity<String> getAssemblyStatus(@PathVariable Long assemblyId){
        return ResponseEntity.ok(assemblyService.getAssemblyStatus(assemblyId));
    }



    @PreAuthorize("hasAuthority('OWNER')")
    @PostMapping("join/{assemblyId}/{passcode}")
    public ResponseEntity<?> joinAssembly(@PathVariable Long assemblyId, @PathVariable int passcode, @RequestHeader("Authorization") String authorizationHeader){
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        String email = jwtService.getEmailFromToken(jwtToken);
        return ResponseEntity.ok(assemblyService.joinAssembly(assemblyId,passcode, email));
    }

    @PutMapping("start/{assemblyId}")
    public ResponseEntity<?> startAssembly(@PathVariable Long assemblyId){
        return ResponseEntity.ok(assemblyService.startAssembly(assemblyId));
    }






}
