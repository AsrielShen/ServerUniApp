package com.example.ServerUniApp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModifyStudentVO {
    private String gender;         // 性别
    private String department;     // 学院
    private String major;          // 专业
    private Integer grade;         // 年级
    private String phone;          // 电话
}
