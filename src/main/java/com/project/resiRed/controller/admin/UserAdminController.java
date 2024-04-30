package com.project.resiRed.controller.admin;

import com.project.resiRed.dto.UserDto;
import com.project.resiRed.service.user.UserAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserAdminController {

    private final UserAdminService userService;

    @GetMapping("getAll")
    public ResponseEntity<List<UserDto>> getAllRegisteredUsers(){
        List<UserDto> userDtoList=userService.getAllUsers();
        return ResponseEntity.ok(userDtoList);
    }
}
