package com.example.ServerUniApp.mapper;

import com.example.ServerUniApp.vo.CourseStudentVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseStudentMapper {

    @Insert("INSERT INTO course_students (course_id, student_id) VALUES (#{courseId}, #{studentId})")
    void insertStudentToCourse(Integer courseId, Integer studentId);

    @Select("""
        SELECT
            s.id AS student_id,
            s.student_number,
            s.student_name,
            s.department,
            s.major,
            s.grade
        FROM students s
        JOIN course_students cs ON cs.student_id = s.id
        WHERE cs.course_id = #{courseId}
    """)
    List<CourseStudentVO> getStudentsByCourseId(@Param("courseId") Integer courseId);

    @Delete("""
        DELETE FROM course_students
        WHERE course_id = #{courseId} AND student_id = #{studentId}
    """)
    int deleteStudentFromCourse(@Param("courseId") Integer courseId, @Param("studentId") Integer studentId);

}
