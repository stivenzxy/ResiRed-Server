package com.project.resiRed.controller.user;

import com.project.resiRed.controller.RegisterRequest;
import com.project.resiRed.dto.AssemblyDto;
import com.project.resiRed.dto.UserDto;
import com.project.resiRed.entity.Assembly;
import com.project.resiRed.entity.User;
import com.project.resiRed.repository.UserRepository;
import com.project.resiRed.service.AuthService;
import com.project.resiRed.service.admin.AssemblyService;
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
    private final AssemblyService assemblyService;

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
    @PostMapping("/attendance")
    public ResponseEntity<UserDto> createAttendance(@RequestParam Long userId, @RequestParam Long assemblyId) {
        System.out.printf("ENTRO 1");
        UserDto userDto = userService.getUser(userId.intValue());
        System.out.printf("ENTRO 2"+ userDto.getFirstname());
        AssemblyDto assembly = assemblyService.getAssemblyById(assemblyId);
        System.out.printf("ENTRO 3");
        if (userDto == null || assembly == null) {
            System.out.printf("NO DEBERIA ESTAR ENTRANDO");
            return ResponseEntity.badRequest().body(null);
        }
        User user = new User(userDto.getUserId(),userDto.getFirstname(), userDto.getLastname(), userDto.getEmail(),userDto.getAddress());
        System.out.printf(" AAA "+ user);
        assembly.getAttendees().add(user);

        assemblyService.updateAttendies(assembly);

        return ResponseEntity.ok(userDto);
    }




    /*@PutMapping()
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest)
    {
        return ResponseEntity.ok(userService.updateUser(userRequest));
    }*/
}