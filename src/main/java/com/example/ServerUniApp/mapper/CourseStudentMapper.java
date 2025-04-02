package com.example.ServerUniApp.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseStudentMapper {

    @Insert("INSERT INTO course_students (course_id, student_id) VALUES (#{courseId}, #{studentId})")
    void insertStudentToCourse(Integer courseId, Integer studentId);

}
