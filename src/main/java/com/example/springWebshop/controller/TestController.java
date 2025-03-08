package com.example.springWebshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String testEndpoint() {
        /**
         * Testing if Spring server works without connecting to a database.
         */
        return "Server is running successfully!";
    }
}
