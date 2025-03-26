package com.example.ServerUniApp.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Auth {
    private Integer id;
    private String userNumber;
    private String password;
    private String openid;
    private String role;
    private LocalDateTime createTime;
}
