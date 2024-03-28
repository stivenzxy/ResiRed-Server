package com.project.resiRed.dto;

import com.project.resiRed.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long userId;
    private String name;
    private String lastname;
    private String address;
    private String password;
    private String email;
    private UserRole role;
}

