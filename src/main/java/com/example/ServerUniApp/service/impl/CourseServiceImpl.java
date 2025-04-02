package com.example.ServerUniApp.service.impl;

import com.example.ServerUniApp.common.JsonResult;
import com.example.ServerUniApp.entity.Course;
import com.example.ServerUniApp.mapper.AuthMapper;
import com.example.ServerUniApp.mapper.CourseMapper;
import com.example.ServerUniApp.mapper.CourseStudentMapper;
import com.example.ServerUniApp.mapper.StudentMapper;
import com.example.ServerUniApp.service.AuthService;
import com.example.ServerUniApp.service.CourseService;
import com.example.ServerUniApp.util.JwtUtil;
import com.example.ServerUniApp.vo.CourseCreateVO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CourseStudentMapper courseStudentMapper;

    @Autowired
    private AuthService authService;


    private void processStudentFile(Integer courseId, MultipartFile file) {
        try (InputStream inputStream = file.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // 读取第一个 sheet
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) continue; // 跳过标题行

                Cell studentNumberCell = row.getCell(0);
                Cell studentNameCell = row.getCell(1);

                if (studentNumberCell == null || studentNameCell == null) continue;

                // 读取数据
                String studentNumber;
                if (studentNumberCell.getCellType() == CellType.NUMERIC) {
                    studentNumber = String.valueOf((long) studentNumberCell.getNumericCellValue()); // 避免小数点
                } else {
                    studentNumber = studentNumberCell.getStringCellValue().trim();
                }
                String studentName = studentNameCell.getStringCellValue().trim();

                if (studentNumber.isEmpty() || studentName.isEmpty()) continue;

                // 注册学生/获得学生id
                Integer studentId = authService.registerStudentFromExcel(studentNumber, studentName);
                courseStudentMapper.insertStudentToCourse(courseId, studentId);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error processing Excel file", e);
        }
    }

    @Override
    @Transactional
    public JsonResult<?> createCourse(CourseCreateVO request) {
        // 先认证身份
        Map<String, Object> userInfo = jwtUtil.getUserInfo(request.getToken());
        if (userInfo == null || !"teacher".equals(userInfo.get("role"))) {
            return JsonResult.error(2, "身份认证错误，请重新登录！");
        }

        // 获得teacher_id创建课程
        Integer teacherId = (Integer) userInfo.get("userId");
        Course course = new Course(null, request.getCourseName(), teacherId, request.getDescription(), null);
        courseMapper.insertCourse(course);

        // 如果学生名单，处理学生名单
//        if (request.getFile() != null && !request.getFile().isEmpty()) {
//            processStudentFile(course.getId(), request.getFile());
//        }

        return JsonResult.success("创建成功！");
    }

    @Override
    public boolean joinCourse(String studentNumber, Integer courseId) {
        return true;
    }

}