package com.project.resiRed.dto.AuthDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetResponse {
    private String message;
    private String type;

    public PasswordResetResponse(String message) {
        this.message = message;
    }
}
