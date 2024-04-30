package com.project.resiRed.controller.admin;

import com.project.resiRed.dto.AssemblyDto.*;
import org.springframework.http.ResponseEntity;
import com.project.resiRed.service.admin.AssemblyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/assembly")
@PreAuthorize("hasAuthority('ADMIN')")
public class AssemblyController {
    private final AssemblyService assemblyService;
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
    public ResponseEntity<List<AssemblyResponse>> getAllAssemblies() {
        return ResponseEntity.ok(assemblyService.getAllAssemblies());
    }


    /*
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('OWNER')")
    @GetMapping("check/")
    public ResponseEntity<AssemblyAvailabilityResponse> checkAvailability(@PathVariable Long assemblyId){
        return ResponseEntity.ok(assemblyService.checkAvailability());
    }
*/





}
