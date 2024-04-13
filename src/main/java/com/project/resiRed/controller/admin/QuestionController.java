package com.project.resiRed.controller.admin;


import com.project.resiRed.dto.ChoiceDto.createChoiceRequest;
import com.project.resiRed.dto.MessageDto;
import com.project.resiRed.dto.QuestionDto.updateQuestionRequest;
import com.project.resiRed.service.admin.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PutMapping(value = "{id}/update")
    public ResponseEntity<MessageDto> updateSurveyQuestion(@PathVariable Long id, @RequestBody updateQuestionRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ADMIN"))) {
            return ResponseEntity.ok(
                    questionService.updateSurveyQuestion(id, request)
            );
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    MessageDto.builder().detail("Insufficient permissions").build());
        }

    }

    @DeleteMapping(value = "{id}/delete")
    public ResponseEntity<MessageDto> deleteQuestion(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ADMIN"))) {
            return ResponseEntity.ok(
                    questionService.deleteQuestion(id)
            );
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    MessageDto.builder().detail("Insufficient permissions").build());
        }

    }

    @DeleteMapping(value = "delete/choice/{id}")
    public ResponseEntity<MessageDto> deleteChoice(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ADMIN"))) {
            return ResponseEntity.ok(
                    questionService.deleteChoice(id)
            );
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    MessageDto.builder().detail("Insufficient permissions").build());
        }

    }

    @PostMapping(value = "{id}/add/choice")
    public  ResponseEntity<MessageDto> addChoiceToQuestion(@PathVariable Long id, @RequestBody createChoiceRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ADMIN"))) {
            return ResponseEntity.ok(
                    questionService.addChoiceToQuestion(id, request)
            );
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    MessageDto.builder().detail("Insufficient permissions").build());
        }

    }
}
