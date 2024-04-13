package com.project.resiRed.dto.AuthDto;

import com.project.resiRed.enums.UserRole;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String name;
    String lastname;
    String address;
    String password;
    String email;
}
