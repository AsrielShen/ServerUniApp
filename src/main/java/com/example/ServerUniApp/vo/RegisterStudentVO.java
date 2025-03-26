package com.example.ServerUniApp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterStudentVO {
    // 主要用于学生注册，小程序前端不提供老师的注册方法
    private String studentNumber;  // 学号
    private String studentName;    // 姓名
    private String password;       //密码
    private String gender;         // 性别
    private String department;     // 学院
    private String major;          // 专业
    private Integer grade;         // 年级
    private String phone;          // 电话
    private String role;           // 身份，默认 "student"
    private String openid;         // 微信号
}