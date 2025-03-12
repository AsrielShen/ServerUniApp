package com.example.ServerUniApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RandomCallController {
    public String query() {
        return "1";
    }
}
