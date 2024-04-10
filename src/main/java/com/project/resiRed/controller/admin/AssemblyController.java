package com.project.resiRed.controller.admin;

import com.project.resiRed.dto.AssemblyDto;
import com.project.resiRed.service.admin.AssemblyService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/assembly")
public class AssemblyController {
    private final AssemblyService assemblyService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("create")
    public ResponseEntity<AssemblyDto> createAssembly(@RequestBody AssemblyDto assemblyDto){
        AssemblyDto createdAssembly=assemblyService.createAssembly(assemblyDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAssembly);
    }
}
