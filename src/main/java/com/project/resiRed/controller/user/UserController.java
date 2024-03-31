package com.project.resiRed.controller.user;

import com.project.resiRed.dto.UserDto;
import com.project.resiRed.entity.User;
import com.project.resiRed.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

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