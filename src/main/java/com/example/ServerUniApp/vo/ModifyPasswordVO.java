package com.example.ServerUniApp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModifyPasswordVO {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
