package com.project.resiRed.controller.user;

import com.project.resiRed.dto.AuthDto.RegisterRequest;
import com.project.resiRed.dto.UserDto;
import com.project.resiRed.entity.User;
import com.project.resiRed.repository.UserRepository;
import com.project.resiRed.service.AuthService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private  final AuthService authService;

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer id)
    {
        UserDto userDTO = userService.getUser(id);
        if (userDTO==null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDTO);
    }




    /*@PutMapping()
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest)
    {
        return ResponseEntity.ok(userService.updateUser(userRequest));
    }*/
}