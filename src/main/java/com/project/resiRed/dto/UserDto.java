package com.project.resiRed.dto;

import com.project.resiRed.entity.User;
import lombok.*;

import java.util.List;

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
    List<AssemblyDto> assemblies;


}