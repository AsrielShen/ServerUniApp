package com.example.ServerUniApp.service;

import com.example.ServerUniApp.vo.AuthResponseVO;
import com.example.ServerUniApp.vo.LoginRequestVO;
import com.example.ServerUniApp.vo.RegisterStudentVO;
import com.example.ServerUniApp.vo.RegisterTeacherVO;

public interface AuthService {
    AuthResponseVO loginByPassword(LoginRequestVO request);
    AuthResponseVO loginByOpenid(String openid);
    AuthResponseVO registerStudent(RegisterStudentVO request);
    Integer registerStudentFromExcel(String studentNumber, String studentName);
    AuthResponseVO registerTeacher(RegisterTeacherVO request);
}