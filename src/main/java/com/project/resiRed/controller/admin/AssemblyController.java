package com.project.resiRed.controller.admin;

import com.project.resiRed.dto.AssemblyDto;
import com.project.resiRed.service.admin.AssemblyService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{assemblyId}")
    public  ResponseEntity<AssemblyDto> deleteAssembly(@PathVariable Long assemblyId){
        System.out.println("ENTRO");
        AssemblyDto deletedAssemblies=assemblyService.deleteAssemblies(assemblyId);
        return ResponseEntity.ok(deletedAssemblies);
    }

    @GetMapping("list")
    public ResponseEntity<List<AssemblyDto>> getAllAssemblies(){
        List<AssemblyDto> allAssemblies=assemblyService.getAllAssemblies();
        return ResponseEntity.ok(allAssemblies);
    }
}
