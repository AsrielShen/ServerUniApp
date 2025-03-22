package com.example.ServerUniApp.mapper;

import com.example.ServerUniApp.entity.Student;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StudentMapper {

    // 添加学生信息
    @Insert("INSERT INTO students (auth_id, student_number, student_name, gender, department, major, grade, phone, role) " +
            "VALUES (#{authId}, #{studentNumber}, #{studentName}, #{gender}, #{department}, #{major}, #{grade}, #{phone}, 'student')")
    void insertStudent(Student student);

    // 认证id查询学生信息
    @Select("SELECT * FROM students WHERE auth_id = #{authId}")
    Student selectByAuthId(Integer authId);

    @Update("UPDATE auth SET openid = #{openid} WHERE id = #{id}")
    void updateOpenid(@Param("id") Integer id, @Param("openid") String openid);

}
