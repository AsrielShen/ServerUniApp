package com.example.ServerUniApp.controller;

import com.example.ServerUniApp.common.JsonResult;
import com.example.ServerUniApp.mapper.AbsenceStatsMapper;
import com.example.ServerUniApp.mapper.CourseStudentMapper;
import com.example.ServerUniApp.mapper.StudentMapper;
import com.example.ServerUniApp.vo.AbsenceRequestVO;
import com.example.ServerUniApp.vo.AbsenceStudentVO;
import com.example.ServerUniApp.vo.CourseStudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseManage")
public class CourseManageController {
    @Autowired
    private AbsenceStatsMapper absenceStatsMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CourseStudentMapper courseStudentMapper;

    @GetMapping("/absence/allStudent/{courseId}")
    public JsonResult<?> getAllAbsenceStudent(@PathVariable Integer courseId) {
        List<AbsenceStudentVO> absenceList = absenceStatsMapper.getAbsenceStudentsByCourseId(courseId);
        return JsonResult.success(absenceList,"操作成功");
    }

    @PostMapping("/absence/decrease")
    public JsonResult<?> decreaseAbsenceCount(@RequestBody AbsenceRequestVO request) {
        Integer studentId = request.getStudentId();

        // 查询当前 absence_count
        Integer count = absenceStatsMapper.getAbsenceCount(request.getCourseId(), studentId);
        if (count == null) {
            return JsonResult.error(2, "未找到该学生的缺勤记录");
        }

        int result;
        if (count > 1) {
            result = absenceStatsMapper.decreaseAbsenceCount(request.getCourseId(), studentId);
        } else {
            result = absenceStatsMapper.deleteAbsence(request.getCourseId(), studentId);
        }

        return result > 0 ? JsonResult.success("操作成功") : JsonResult.error("操作失败");
    }

    @PostMapping("/absence/add")
    public JsonResult<?> addAbsence(@RequestBody AbsenceRequestVO request) {
        Integer studentId = request.getStudentId();

        Integer count = absenceStatsMapper.getAbsenceCount(request.getCourseId(), studentId);

        int result;
        if (count == null) {
            // 插入新记录
            result = absenceStatsMapper.insertAbsence(request.getCourseId(), studentId);
        } else {
            // 已存在则+1
            result = absenceStatsMapper.increaseAbsenceCount(request.getCourseId(), studentId);
        }

        return result > 0 ? JsonResult.success("添加成功") : JsonResult.error("操作失败");
    }

    @GetMapping("/students/{courseId}")
    public JsonResult<?> getStudentsByCourse(@PathVariable Integer courseId) {
        List<CourseStudentVO> students = courseStudentMapper.getStudentsByCourseId(courseId);
        return JsonResult.success(students,"操作成功");
    }

    @GetMapping("/deleteStudent/course/{courseId}/student/{studentId}")
    public JsonResult<?> deleteStudentFromCourse(@PathVariable Integer courseId, @PathVariable Integer studentId) {
        int rows = courseStudentMapper.deleteStudentFromCourse(courseId, studentId);
        if (rows > 0) {
            return JsonResult.success("学生已从课堂移除");
        } else {
            return JsonResult.error("移除失败，未找到匹配项");
        }
    }
}
