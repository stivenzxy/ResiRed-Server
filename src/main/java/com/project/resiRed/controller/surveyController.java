package com.project.resiRed.controller;


import com.project.resiRed.service.SurveyService;
import com.project.resiRed.entity.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.resiRed.dto.SurveyDto;
import com.project.resiRed.dto.MessageDto;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class surveyController {
    private final SurveyService surveyService;

    @PostMapping(value = "create")
    public  ResponseEntity<MessageDto> createSurvey(@RequestBody SurveyDto surveyDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ADMIN"))) {
            return ResponseEntity.ok(
                    surveyService.createSurvey(surveyDto, userDetails.getUsername())
            );
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    MessageDto.builder().detail("Insufficient permissions").build());
        }

    }
}
