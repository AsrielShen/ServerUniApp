package com.example.ServerUniApp.mapper;

import com.example.ServerUniApp.entity.Student;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StudentMapper {

    // 通过 authId 查询学生信息
    @Select("SELECT * FROM students WHERE auth_id = #{authId}")
    Student findByAuthId(Integer authId);


    // 添加学生信息
    @Insert("INSERT INTO students (auth_id, student_number, student_name, gender, department, major, grade, phone, role) " +
            "VALUES (#{authId}, #{studentNumber}, #{studentName}, #{gender}, #{department}, #{major}, #{grade}, #{phone}, 'student')")
    @Options(useGeneratedKeys = true, keyProperty = "id")  // 确保 id 自动填充
    void insertStudent(Student student);


}
