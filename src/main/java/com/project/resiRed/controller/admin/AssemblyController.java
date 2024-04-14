package com.project.resiRed.controller.admin;

import org.springframework.http.ResponseEntity;
import com.project.resiRed.dto.AssemblyDto.createAssemblyRequest;
import com.project.resiRed.service.admin.AssemblyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/assembly")
@PreAuthorize("hasAuthority('ADMIN')")
public class AssemblyController {
    private final AssemblyService assemblyService;
    @PostMapping("create")
    public ResponseEntity<?> createAssembly(@RequestBody createAssemblyRequest createAssemblyRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                assemblyService.createAssembly(createAssemblyRequest)
        );
    }
}
