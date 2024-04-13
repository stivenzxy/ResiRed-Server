package com.project.resiRed.controller;

import com.project.resiRed.dto.AuthDto.AuthResponse;
import com.project.resiRed.dto.AuthDto.LoginRequest;
import com.project.resiRed.dto.AuthDto.RegisterRequest;
import com.project.resiRed.dto.RefreshTokenDto;
import com.project.resiRed.entity.User;
import com.project.resiRed.repository.RefreshTokenRepository;
import com.project.resiRed.repository.UserRepository;
import com.project.resiRed.service.AuthService;
import com.project.resiRed.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    @PostMapping(value="login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        AuthResponse auth = authService.login(request);
        return ResponseEntity.ok(auth);
    }
    @PostMapping(value = "register")
    public  ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        String refreshToken = refreshTokenDto.getRefreshToken(); // Obteniendo el refresh token del DTO
        // Verifica si el refresh token es válido
        String username = jwtService.getUsernameFromRefreshToken(refreshToken);
        if (username == null || jwtService.isRefreshTokenExpired(refreshToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid or expired refresh token");
        }

        // Busca al usuario basado en el username obtenido del refresh token
        User user = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        String newAccessToken = jwtService.getToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user); // Opcionalmente, genera un nuevo refresh token

        // Devuelve los tokens
        return ResponseEntity.ok(new AuthResponse(newAccessToken, newRefreshToken));
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(@RequestBody RefreshTokenDto refreshTokenDto) {
        String refreshToken = refreshTokenDto.getRefreshToken();
        // Elimina el token de actualización de la base de datos
        refreshTokenRepository.findByToken(refreshToken).ifPresent(refreshTokenRepository::delete);
        return ResponseEntity.ok().build(); // Responde con el estado OK después de la eliminación
    }

}
