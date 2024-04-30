package com.project.resiRed.controller.admin;

import com.project.resiRed.dto.AssemblyDto;
import com.project.resiRed.dto.UserDto;
import com.project.resiRed.entity.Assembly;
import com.project.resiRed.entity.User;
import com.project.resiRed.service.admin.AssemblyService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    @GetMapping("/{assemblyId}/attendances")
    public ResponseEntity<List<UserDto>> getAttendances(@PathVariable Long assemblyId) {
        AssemblyDto assembly = assemblyService.getAssemblyById(assemblyId);
        if (assembly == null) {
            return ResponseEntity.notFound().build();
        }
        List<User> attendees =assembly.getAttendees();

        List<UserDto> attendesToAssembly=new ArrayList<>();
        for(User user:attendees){
            attendesToAssembly.add(user.getDto());
            System.out.println("USER: "+user);
        }
        return ResponseEntity.ok(attendesToAssembly);
    }

}
