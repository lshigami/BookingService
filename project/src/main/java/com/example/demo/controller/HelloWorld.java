package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/hello")
public class HelloWorld {
    @GetMapping
    @RequestMapping("/hello")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
