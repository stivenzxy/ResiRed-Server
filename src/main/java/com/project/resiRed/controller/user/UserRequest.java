package com.project.resiRed.controller.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    int userId;
    String email;
    String firstname;
    String lastname;
    String address;
}
