package com.example.ServerUniApp.service;

import com.example.ServerUniApp.common.JsonResult;
import com.example.ServerUniApp.entity.Course;
import com.example.ServerUniApp.vo.CourseCreateVO;
import org.springframework.web.multipart.MultipartFile;

public interface CourseService {

    JsonResult<?> createCourse(CourseCreateVO courseCreateVO);

    boolean joinCourse(String studentNumber, Integer courseId);
    //    void processStudentFile(Integer courseId, MultipartFile file);
}
