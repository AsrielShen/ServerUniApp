package com.example.ServerUniApp.controller;

import com.example.ServerUniApp.common.JsonResult;
import com.example.ServerUniApp.entity.Auth;
import com.example.ServerUniApp.entity.Student;
import com.example.ServerUniApp.entity.Teacher;
import com.example.ServerUniApp.mapper.AuthMapper;
import com.example.ServerUniApp.mapper.StudentMapper;
import com.example.ServerUniApp.mapper.TeacherMapper;
import com.example.ServerUniApp.util.JwtUtil;
import com.example.ServerUniApp.vo.AuthResponseVO;
import com.example.ServerUniApp.vo.ModifyPasswordVO;
import com.example.ServerUniApp.vo.ModifyStudentVO;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/getInfo/{token}")
    public JsonResult<?> getUserInfo(@PathVariable String token) {
        Map<String, Object> userInfo = jwtUtil.getUserInfo(token);
        if (userInfo == null) {
            return JsonResult.error(2, "身份认证错误，请重新登录！");
        }
        Integer userId = (Integer) userInfo.get("userId");
        if("teacher".equals(userInfo.get("role"))) {
            Teacher teacher =  teacherMapper.findById(userId);
            return JsonResult.success(teacher,"获得学生信息！");
        }
        else if("student".equals(userInfo.get("role"))) {
            Student student =  studentMapper.findById(userId);
            return JsonResult.success(student,"获得学生信息！");
        }
        else {
            return JsonResult.error(2, "身份认证错误，请重新登录！");
        }
    }

    @PostMapping("/modify/student/{token}")
    public JsonResult<?> modifyStudentInfo(@PathVariable String token, @RequestBody ModifyStudentVO request) {
        Map<String, Object> userInfo = jwtUtil.getUserInfo(token);
        if (userInfo == null) {
            return JsonResult.error(2, "身份认证错误，请重新登录！");
        }
        Integer userId = (Integer) userInfo.get("userId");
        Student student = new Student(userId,null,null,null,request.getGender(),request.getDepartment(),request.getMajor(),request.getGrade(), request.getPhone(), "student",null);
        studentMapper.updateStudent(student);
        return JsonResult.success();
    }

    @PostMapping("/modify/password/{token}")
    public JsonResult<?> modifyPassword(@PathVariable String token, @RequestBody ModifyPasswordVO request) {
        Map<String, Object> userInfo = jwtUtil.getUserInfo(token);
        if (userInfo == null) {
            return JsonResult.error(2, "身份认证错误，请重新登录！");
        }
        Integer userId = (Integer) userInfo.get("userId");
        Integer authId;
        if("teacher".equals(userInfo.get("role"))) {
            Teacher teacher =  teacherMapper.findById(userId);
            authId = teacher.getAuthId();
        }
        else if("student".equals(userInfo.get("role"))) {
            Student student =  studentMapper.findById(userId);
            authId = student.getAuthId();
        }
        else {
            return JsonResult.error(2, "身份认证错误，请重新登录！");
        }

        Auth auth = authMapper.findById(authId);
        if (!BCrypt.checkpw(request.getOldPassword(), auth.getPassword())) {
            return JsonResult.error(1,"密码错误！");
        }
        String hashedPassword = BCrypt.hashpw(request.getNewPassword(), BCrypt.gensalt());
        authMapper.updatePassword(authId,hashedPassword);
        return JsonResult.success();
    }
}
