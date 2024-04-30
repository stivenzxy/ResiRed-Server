package com.project.resiRed.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    int userId;
    String email;
    String firstname;
    String lastname;
    String address;
}