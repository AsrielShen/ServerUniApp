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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        if (request.getFile() != null && !request.getFile().isEmpty()) {
            processStudentFile(course.getId(), request.getFile());
        }

        return JsonResult.success("创建成功！");
    }

    private void processStudentFile(Integer courseId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("学生名单文件不能为空");
        }

        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0); // 默认读取第一个 Sheet

            // 遍历行（第一行作为表头跳过）
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    // 跳过第一行
                    continue;
                }

                // 读取每行数据
                String studentNumber = getCellStringValue(row.getCell(0));  // 第一列：学号
                String studentName = getCellStringValue(row.getCell(1));    // 第二列：姓名

                // 检验数据有效性
                if (studentNumber == null || studentNumber.trim().isEmpty()) {
                    throw new RuntimeException("第 " + (row.getRowNum() + 1) + " 行学号为空");
                }
                if (studentName == null || studentName.trim().isEmpty()) {
                    throw new RuntimeException("第 " + (row.getRowNum() + 1) + " 行姓名为空");
                }

                // 注册/获得学生id，并将其添加到对应course中
                try {
                    Integer studentId = authService.registerStudentFromExcel(studentNumber, studentName);
                    courseStudentMapper.insertStudentToCourse(courseId, studentId);
                } catch (Exception e) {
                    // 记录错误（例如学号重复
                    throw new RuntimeException("第 " + (row.getRowNum() + 1) + " 行处理失败: " + e.getMessage(), e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Excel 文件解析失败: " + e.getMessage(), e);
        }
    }

    // 获取每一个cell的字符值（防止学号被默认为数字
    private String getCellStringValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());
            case BLANK:
                return null;
            default:
                throw new RuntimeException("不支持的单元格类型: " + cell.getCellType());
        }
    }


    @Override
    public boolean joinCourse(String studentNumber, Integer courseId) {
        return true;
    }

}