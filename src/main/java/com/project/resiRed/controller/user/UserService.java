package com.project.resiRed.controller.user;

import com.project.resiRed.dto.UserDto;
import com.project.resiRed.entity.User;
import com.project.resiRed.enums.UserRole;
import com.project.resiRed.repository.UserRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /*@Transactional
    public UserResponse updateUser(UserRequest userRequest) {

        User user = User.builder()
                .userId((long) userRequest.getUserId())
                .name(userRequest.getFirstname())
                .lastname(userRequest.getLastname())
                .address(userRequest.getAddress())
                .role(UserRole.OWNER)
                .build();

        userRepository.updateUser(Math.toIntExact(user.getUserId()), user.getFirstName(), user.getLastname(), user.getAddress());

        return new UserResponse("El usuario se registró satisfactoriamente");
    }*/

    public UserDto getUser(Integer id) {
        User user = userRepository.findById(Long.valueOf(id)).orElse(null);

        if (user!=null)
        {
            return UserDto.builder()
                    .userId(Math.toIntExact(user.getUserId()))
                    .email(user.getEmail())
                    .firstname(user.getName())
                    .lastname(user.getLastname())
                    .address(user.getAddress())
                    .build();
        }
        return null;
    }
}
