package com.example.ServerUniApp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Auth {
    private Integer id;         // 用户ID
    private String userNumber;  // 用户学号/工号
    private String password;    // 密码
    private String openid;      // 微信OpenID
    private String token;       // 登录Token
    private String role;        // 用户角色：student 或 teacher
    private LocalDateTime createTime;  // 创建时间
}
