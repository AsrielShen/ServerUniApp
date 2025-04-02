package com.example.ServerUniApp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterTeacherVO {
    private String teacherNumber;  // 学号
    private String teacherName;    // 姓名
    private String password;       //密码
    private String gender;         // 性别
    private String department;     // 学院
    private String phone;          // 电话
    private String role;           // 身份，默认 "teacher"
    private String openid;         // 微信号
}
