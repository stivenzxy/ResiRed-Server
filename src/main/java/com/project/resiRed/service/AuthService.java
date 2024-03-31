package com.project.resiRed.service;

import com.project.resiRed.controller.AuthResponse;
import com.project.resiRed.controller.LoginRequest;
import com.project.resiRed.controller.RegisterRequest;
import com.project.resiRed.entity.User;
import com.project.resiRed.enums.UserRole;
import com.project.resiRed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findUserByEmail(request.getEmail()).orElseThrow();
        String accessToken = jwtService.getToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .lastname(request.getLastname())
                .address(request.getAddress())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(UserRole.OWNER)
                .build();
        userRepository.save(user);
        String accessToken = jwtService.getToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
