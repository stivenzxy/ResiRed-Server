package com.project.resiRed.controller.user;

import com.project.resiRed.dto.AuthDto.RegisterRequest;
import com.project.resiRed.dto.UserDto;
import com.project.resiRed.entity.User;
import com.project.resiRed.enums.UserRole;
import com.project.resiRed.repository.UserRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDto> getUsersByRole(UserRole role) {
        List<User> users = userRepository.findByRole(role);
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(UserDto.builder()
                    .userId(Math.toIntExact(user.getUserId()))
                    .email(user.getEmail())
                    .firstname(user.getName())
                    .lastname(user.getLastname())
                    .address(user.getAddress())
                    .build());
        }
        return userDtos;
    }

}
