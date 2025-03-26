package com.example.ServerUniApp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Teacher {
    private Integer id;             // 教师ID
    private Integer authId;         // 关联 auth 表的 ID
    private String teacherNumber;   // 工号
    private String teacherName;     // 姓名
    private String gender;          // 性别
    private String department;      // 学院
    private String phone;           // 电话
    private String role;            // 身份，默认 "teacher"
    private LocalDateTime createTime;  // 创建时间
}