package com.example.ServerUniApp.controller;

import com.example.ServerUniApp.common.JsonResult;
import com.example.ServerUniApp.service.CourseService;
import com.example.ServerUniApp.vo.CourseCreateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/create")
    public JsonResult<?> createCourse(@RequestBody CourseCreateVO request) {
        return courseService.createCourse(request);
    }
//
//    @PostMapping("/join")
//    public JsonResult<?> joinCourse() {
//
//    }


}
