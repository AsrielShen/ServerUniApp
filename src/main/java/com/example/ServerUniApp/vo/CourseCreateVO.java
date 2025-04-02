package com.example.ServerUniApp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class CourseCreateVO {
    private String courseName;
    private String token; //身份认证
    private String description;
    private MultipartFile file;
}
