package com.project.resiRed.controller.user;

import com.project.resiRed.dto.UserDto;
import com.project.resiRed.enums.UserRole;
import com.project.resiRed.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping(value = "/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    private final UserService userService;
    private  final AuthService authService;

    @GetMapping("/owners")
    public ResponseEntity<List<UserDto>> getOwners() {
        List<UserDto> userDtos = userService.getUsersByRole(UserRole.OWNER);
        if (userDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userDtos);
    }
}