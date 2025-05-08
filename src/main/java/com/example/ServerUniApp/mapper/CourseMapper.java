package com.example.ServerUniApp.mapper;


import com.example.ServerUniApp.entity.Course;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Select("SELECT * FROM courses WHERE id = #{id}")
    Course findById(Integer id);

    @Select("SELECT * FROM courses WHERE teacher_id = #{teacherId}")
    List<Course> findAllCourseByTeacherId(Integer id);

    @Insert("INSERT INTO courses (course_name, teacher_id, description) VALUES (#{courseName}, #{teacherId}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertCourse(Course course);
}
