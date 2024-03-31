package com.project.resiRed.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class demoController {
    @GetMapping (value="demo")
    public Map<String, String> welcome(){
        return Collections.singletonMap("message", "welcome from secure endpoint");
    }
}