package com.example.ServerUniApp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseVO {
    private Integer userId;
    private String userNumber;
    private String userName;
    private String role;
    private String token;
}
