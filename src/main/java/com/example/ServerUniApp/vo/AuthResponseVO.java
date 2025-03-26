package com.example.ServerUniApp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseVO {
    private Integer userId; // 考虑是否要返回前端（主要是意义不大，需要使用到userId的部分都需要用token来进行认证的，而不是userId)
    private String userNumber;
    private String userName;
    private String role;
    private String token; // 最重要的认证信息
    private Integer code; //0 成功 1 失败
    private String msg; //成功失败等信息
}