package com.project.resiRed.controller;

import com.project.resiRed.dto.UserDto;
import com.project.resiRed.enums.UserRole;
import com.project.resiRed.service.authentication.AuthService;
import com.project.resiRed.service.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping(value = "/api/admin/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    private  final AuthService authService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/owners")
    public ResponseEntity<List<UserDto>> getOwners() {
        List<UserDto> userDtos = userService.getUsersByRole(UserRole.OWNER);
        if (userDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userDtos);
    }
}