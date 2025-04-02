package com.example.ServerUniApp.mapper;


import com.example.ServerUniApp.entity.Course;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CourseMapper {

    @Select("SELECT * FROM courses WHERE teacher_id = #{teacherId}")
    Course findByTeacherId(Integer teacherId);

    @Insert("INSERT INTO courses (course_name, teacher_id, description) VALUES (#{courseName}, #{teacherId}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertCourse(Course course);
}
