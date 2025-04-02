package com.example.ServerUniApp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Course {
    private Integer id;
    private String courseName;
    private Integer teacherId;
    private String description;
    private LocalDateTime createTime;
}
