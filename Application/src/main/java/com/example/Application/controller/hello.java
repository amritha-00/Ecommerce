package com.example.Application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class hello {

    @GetMapping("/test")
    public String greet(){
        return "Working, don't worry";
    }
}
