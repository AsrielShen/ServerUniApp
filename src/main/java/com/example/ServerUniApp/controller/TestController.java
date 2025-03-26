package com.example.ServerUniApp.controller;

import com.example.ServerUniApp.entity.Auth;
import com.example.ServerUniApp.mapper.AuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @Autowired
    AuthMapper authMapper;

    @GetMapping("/auth/id/{id}")
    public Auth auth(@PathVariable Integer id) {
        return authMapper.findById(id);
    }

    @GetMapping("/auth/openid/{openid}")
    public Auth authByOpenid(@PathVariable String openid) {
        System.out.println("Received openid: " + openid);
        return authMapper.findByOpenid(openid);
    }

    @GetMapping("/auth/userNumber/{userNumber}")
    public Auth authByUserNumber(@PathVariable String userNumber) {
        return authMapper.findByUserNumber(userNumber);
    }

}
