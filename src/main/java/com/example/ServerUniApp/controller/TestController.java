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

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private CourseMapper courseMapper;

    @GetMapping("/hello")
    public String hello() {
        return "hello world!";
    }

    @PostMapping("/course/create")
    public Course courseCreate(@RequestBody Course request) {
        Course ans = new Course(null, request.getCourseName(), request.getTeacherId(), request.getDescription(), null);
        courseMapper.insertCourse(ans);
        return ans;
    }

    @GetMapping("/auth/studentNumber/studentName/{studentNumber}/{studentName}")
    public Integer registerStudentByExcel(@PathVariable String studentNumber,@PathVariable String studentName) {
        return authService.registerStudentFromExcel(studentNumber, studentName);
    }

}
