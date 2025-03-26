package com.example.ServerUniApp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Student {
    private Integer id;
    private Integer authId;        // 关联 auth 表的 ID
    private String studentNumber;  // 学号
    private String studentName;    // 姓名
    private String gender;         // 性别
    private String department;     // 学院
    private String major;          // 专业
    private Integer grade;         // 年级
    private String phone;          // 电话
    private String role;           // 身份，默认 "student"
    private LocalDateTime createTime;  // 创建时间
}