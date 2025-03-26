package com.example.ServerUniApp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestVO {
    private String userNumber;  // 学号 / 教师工号
    private String password;
    private String openid;
}