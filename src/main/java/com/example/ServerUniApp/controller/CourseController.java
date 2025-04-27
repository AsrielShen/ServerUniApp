package com.example.ServerUniApp.controller;

import com.example.ServerUniApp.common.JsonResult;
import com.example.ServerUniApp.entity.Course;
import com.example.ServerUniApp.mapper.CourseMapper;
import com.example.ServerUniApp.mapper.CourseStudentMapper;
import com.example.ServerUniApp.service.CourseService;
import com.example.ServerUniApp.util.JwtUtil;
import com.example.ServerUniApp.vo.CourseCreateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseStudentMapper courseStudentMapper;

    @Autowired
    private CourseService courseService;

    @PostMapping("/create")
    public JsonResult<?> createCourse(CourseCreateVO request) {
        return courseService.createCourse(request);
    }

    @GetMapping("/getById/{courseId}")
    public JsonResult<?> getById(@PathVariable Integer courseId) {
        Course course = courseMapper.findById(courseId);
        if(course == null) {
            return JsonResult.error();
        }
        else return JsonResult.success(course,"操作成功");
    }

    @PostMapping("/join/{token}")
    public JsonResult<?> joinCourse(@PathVariable String token, @RequestBody Integer courseId) {
        Map<String, Object> userInfo = jwtUtil.getUserInfo(token);
        if (userInfo == null) {
            return JsonResult.error(2, "身份认证错误，请重新登录！");
        }
        Integer userId = (Integer) userInfo.get("userId");
        courseStudentMapper.insertStudentToCourse(courseId,userId);
        return JsonResult.success();
    }

}
