package com.example.ServerUniApp.mapper;

import com.example.ServerUniApp.entity.Student;
import com.example.ServerUniApp.entity.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TeacherMapper {

    // 通过 authId 查询老师
    @Select("SELECT * FROM teachers WHERE auth_id = #{authId}")
    Teacher findByAuthId(Integer authId);

    @Select("SELECT * FROM teachers WHERE id = #{id}")
    Teacher findById(Integer id);

    // 添加老师
    @Insert("INSERT INTO teachers (auth_id, teacher_number, teacher_name, gender, department, phone, role) " +
            "VALUES (#{authId}, #{teacherNumber}, #{teacherName}, #{gender}, #{department}, #{phone}, 'teacher')")
    void insertTeacher(Teacher teacher);


}
