package com.project.resiRed.service;

import com.project.resiRed.dto.UserDto;
import com.project.resiRed.enums.UserRole;

import java.util.List;

public interface UserService {
    List<UserDto> getUsersByRole(UserRole role);
}
