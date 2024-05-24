package com.project.resiRed.controller;

import com.project.resiRed.dto.AuthDto.*;
import com.project.resiRed.dto.RefreshTokenDto;
import com.project.resiRed.dto.UserDto;
import com.project.resiRed.entity.PasswordResetToken;
import com.project.resiRed.entity.User;
import com.project.resiRed.repository.PasswordResetTokenRepository;
import com.project.resiRed.repository.RefreshTokenRepository;
import com.project.resiRed.repository.UserRepository;
import com.project.resiRed.service.AuthService;
import com.project.resiRed.service.EmailService;
import com.project.resiRed.service.authentication.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("registerUsers")
    ResponseEntity<?> registerUsers(@RequestParam("file") MultipartFile file){
        if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".csv")) {
            RegisterResponse errorResponse = new RegisterResponse("El archivo debe ser un archivo CSV.");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        //System.out.println("Entro");
        List<RegisterRequest> usersToSave=new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream())); CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';'))) {
            CSVRecord header = csvParser.iterator().next();
            int expectedColumnCount = 5; // Número esperado de columnas

            if (header.size() != expectedColumnCount) {
                RegisterResponse errorResponse = new RegisterResponse("El archivo debe contener exactamente " + expectedColumnCount + " columnas.");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            for (CSVRecord csvRecord : csvParser) {
                //System.out.println("ENTRO V2");
                RegisterRequest request = new RegisterRequest();
                request.setName(csvRecord.get(0));
                System.out.println(request.getName());
                request.setLastname(csvRecord.get(1));
                request.setAddress(csvRecord.get(2));
                request.setPassword(csvRecord.get(3));
                request.setEmail(csvRecord.get(4));
                authService.register(request);
                usersToSave.add(request);
            }


        } catch (Exception e) {
            System.err.println("Error!!: " + e.getMessage());
        }
        return ResponseEntity.ok(usersToSave);

    }

    @PreAuthorize("permitAll()")
    @PostMapping("requestPasswordCode")
    public ResponseEntity<?> sendEmail(@RequestBody ResetPasswordCodeRequest request) {
        String email = request.getEmail();
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (!userOptional.isPresent()) {
            PasswordResetResponse errorResponse = new PasswordResetResponse("Correo electrónico no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        try {
            emailService.sendEmail(email);
            PasswordResetResponse errorResponse = new PasswordResetResponse("Se ha enviado un codigo de reestablecimiento a tu email");
            return ResponseEntity.status(HttpStatus.OK).body(errorResponse);

        } catch (Exception e) {
            PasswordResetResponse errorResponse = new PasswordResetResponse("Error al enviar el correo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PreAuthorize("permitAll()")
    @PostMapping("resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest request) {
        PasswordResetToken token = passwordResetTokenRepository.findByToken(request.getToken())
                .orElse(null);

        if (token == null || token.getExpirationDate().before(new Date())) {
            PasswordResetResponse errorResponse = new PasswordResetResponse("El Token es inválido o ha expirado!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        User user = token.getUser();
        if (user == null) {
            PasswordResetResponse errorResponse = new PasswordResetResponse("El usuario no ha sido encontrado!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        passwordResetTokenRepository.delete(token);

        PasswordResetResponse errorResponse = new PasswordResetResponse("Contraseña actualizada con éxito");
        return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
    }

}