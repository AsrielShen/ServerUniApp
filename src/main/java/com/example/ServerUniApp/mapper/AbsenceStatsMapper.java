package com.example.ServerUniApp.mapper;

import com.example.ServerUniApp.vo.AbsenceStudentVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AbsenceStatsMapper {
    @Select("""
        SELECT
            s.id AS student_id,
            s.student_number,
            s.student_name,
            a.absence_count
        FROM absence_stats a
        JOIN students s ON a.student_id = s.id
        WHERE a.course_id = #{courseId}
    """)
    List<AbsenceStudentVO> getAbsenceStudentsByCourseId(@Param("courseId") Integer courseId);

    @Select("""
        SELECT absence_count
        FROM absence_stats
        WHERE course_id = #{courseId} AND student_id = #{studentId}
    """)
    Integer getAbsenceCount(@Param("courseId") Integer courseId, @Param("studentId") Integer studentId);

    @Update("""
        UPDATE absence_stats
        SET absence_count = absence_count - 1
        WHERE course_id = #{courseId} AND student_id = #{studentId}
    """)
    int decreaseAbsenceCount(@Param("courseId") Integer courseId, @Param("studentId") Integer studentId);

    @Delete("""
        DELETE FROM absence_stats
        WHERE course_id = #{courseId} AND student_id = #{studentId}
    """)
    int deleteAbsence(@Param("courseId") Integer courseId, @Param("studentId") Integer studentId);

    @Insert("""
        INSERT INTO absence_stats (course_id, student_id, absence_count) 
        VALUES (#{courseId}, #{studentId}, 1)
    """)
    int insertAbsence(@Param("courseId") Integer courseId, @Param("studentId") Integer studentId);

    @Update("""
        UPDATE absence_stats 
        SET absence_count = absence_count + 1 
        WHERE course_id = #{courseId} AND student_id = #{studentId}
    """)
    int increaseAbsenceCount(@Param("courseId") Integer courseId, @Param("studentId") Integer studentId);

}
