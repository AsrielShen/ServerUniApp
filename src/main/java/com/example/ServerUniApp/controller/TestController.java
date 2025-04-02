package com.example.ServerUniApp.controller;

import com.example.ServerUniApp.entity.Auth;
import com.example.ServerUniApp.entity.Course;
import com.example.ServerUniApp.mapper.AuthMapper;
import com.example.ServerUniApp.mapper.CourseMapper;
import com.example.ServerUniApp.service.AuthService;
import com.example.ServerUniApp.vo.CourseCreateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private CourseMapper courseMapper;

    @PostMapping("/course/create")
    public Course courseCreate(@RequestBody Course request) {
        courseMapper.insertCourse(request);
        return request;
    }

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

    @GetMapping("/auth/studentNumber/studentName/{studentNumber}/{studentName}")
    public Integer registerStudentByExcel(@PathVariable String studentNumber,@PathVariable String studentName) {
        return authService.registerStudentFromExcel(studentNumber, studentName);
    }

}
