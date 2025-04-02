package com.example.ServerUniApp.service.impl;

import com.example.ServerUniApp.entity.Auth;
import com.example.ServerUniApp.entity.Student;
import com.example.ServerUniApp.entity.Teacher;
import com.example.ServerUniApp.mapper.AuthMapper;
import com.example.ServerUniApp.mapper.StudentMapper;
import com.example.ServerUniApp.mapper.TeacherMapper;
import com.example.ServerUniApp.service.AuthService;
import com.example.ServerUniApp.util.JwtUtil;
import com.example.ServerUniApp.vo.AuthResponseVO;
import com.example.ServerUniApp.vo.LoginRequestVO;
import com.example.ServerUniApp.vo.RegisterStudentVO;
import com.example.ServerUniApp.vo.RegisterTeacherVO;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    // 公共登录逻辑
    private AuthResponseVO login(Auth auth) {
        if ("student".equals(auth.getRole())) {
            Student user = studentMapper.findByAuthId(auth.getId());
            String token = jwtUtil.getToken(user.getId(), user.getRole());
            return new AuthResponseVO(user.getId(), user.getStudentNumber(), user.getStudentName(), user.getRole(), token, 0, "登陆成功！");
        } else if ("teacher".equals(auth.getRole())) {
            Teacher user = teacherMapper.findByAuthId(auth.getId());
            String token = jwtUtil.getToken(user.getId(), user.getRole());
            return new AuthResponseVO(user.getId(), user.getTeacherNumber(), user.getTeacherName(), user.getRole(), token, 0, "登陆成功！");
        } else {
            return new AuthResponseVO(null, null, null, null, null, 1, "登录失败！");
        }
    }

    @Override
    public AuthResponseVO loginByPassword(LoginRequestVO request) {
        // 登录认证
        Auth auth = authMapper.findByUserNumber(request.getUserNumber());
        if (auth == null) {
            return new AuthResponseVO(null, request.getUserNumber(), null, null, null, 1, "账号未注册！");
        }

        if (!BCrypt.checkpw(request.getPassword(), auth.getPassword())) {
            return new AuthResponseVO(null, request.getUserNumber(), null, null, null, 1, "密码错误！");
        }

        // 更新 openid
        authMapper.updateOpenid(auth.getId(), request.getOpenid());

        // 生成 Token，并返回
        return login(auth);
    }

    @Override
    public AuthResponseVO loginByOpenid(String openid) {
        System.out.println("Received openid: " + openid);
        Auth auth = authMapper.findByOpenid(openid);
        if (auth == null) {
            return new AuthResponseVO(null, null, null, null, null, 1, "该微信没有绑定账号！");
        }
        // 生成 Token，并返回
        return login(auth);
    }

    @Override
    @Transactional
    public AuthResponseVO registerStudent(RegisterStudentVO request) {
        if (authMapper.findByUserNumber(request.getStudentNumber()) != null) {
            return new AuthResponseVO(null, request.getStudentNumber(), null, null, null, 1, "该学号已被注册！");
        }

        // 密码加密
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        // 添加 auth 和 student
        // 创建 auth
        Auth auth = new Auth(null, request.getStudentNumber(), hashedPassword, request.getOpenid(), "student", null);
        authMapper.insertAuth(auth);

        // 创建 student
        Integer authId = auth.getId();
        Student user = new Student(null, authId, request.getStudentNumber(), request.getStudentName(),
                request.getGender(), request.getDepartment(), request.getMajor(), request.getGrade(),
                request.getPhone(), "student", null);
        studentMapper.insertStudent(user);

        // 生成 Token，并返回
        String token = jwtUtil.getToken(user.getId(), user.getRole());
        return new AuthResponseVO(user.getId(), user.getStudentNumber(), user.getStudentName(), user.getRole(), token, 0, "注册成功！");
    }

    @Override
    @Transactional
    public Integer registerStudentFromExcel(String studentNumber, String studentName){
        // 检查当前学生是否注册过账号
        Auth existingAuth = authMapper.findByUserNumber(studentNumber);
        if(existingAuth != null) {
            //已存在直接寻找学生id
            return studentMapper.findByAuthId(existingAuth.getId()).getId();
        }

        // 若学生未注册账号，则默认创建auth和student
        // 默认密码（使用默认密码帮同学创建账号）
        String defaultPassword = BCrypt.hashpw("123456789", BCrypt.gensalt());

        // 添加 auth 和 student
        // 添加 auth
        Auth auth = new Auth(null, studentNumber, defaultPassword, null, "student", null);
        authMapper.insertAuth(auth);

        // 添加 student
        Integer authId = auth.getId();
        Student student = new Student(null, authId, studentNumber, studentName, null, null, null, null, null, "student", null);
        studentMapper.insertStudent(student);

        return student.getId();
    }

    @Override
    @Transactional
    public AuthResponseVO registerTeacher(RegisterTeacherVO request) {
        if (authMapper.findByUserNumber(request.getTeacherNumber()) != null) {
            return new AuthResponseVO(null, request.getTeacherNumber(), null, null, null, 1, "该工号/学号已被注册！");
        }

        // 密码加密
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        // 添加 auth 和 teacher
        // 创建 auth
        Auth auth = new Auth(null, request.getTeacherNumber(), hashedPassword, request.getOpenid(), "teacher", null);
        authMapper.insertAuth(auth);

        // 创建 teacher
        Integer authId = auth.getId();
        Teacher user = new Teacher(null, authId, request.getTeacherNumber(), request.getTeacherName(),
                request.getGender(), request.getDepartment(), request.getPhone(), "teacher", null);
        teacherMapper.insertTeacher(user);

        // 生成 Token，并返回
        String token = jwtUtil.getToken(user.getId(), user.getRole());
        return new AuthResponseVO(user.getId(), user.getTeacherNumber(), user.getTeacherName(), user.getRole(), token, 0, "注册成功！");
    }

}
