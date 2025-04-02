package com.example.ServerUniApp.controller;


import com.example.ServerUniApp.common.JsonResult;
import com.example.ServerUniApp.mapper.AuthMapper;
import com.example.ServerUniApp.service.AuthService;
import com.example.ServerUniApp.vo.AuthResponseVO;
import com.example.ServerUniApp.vo.LoginRequestVO;
import com.example.ServerUniApp.vo.RegisterStudentVO;
import com.example.ServerUniApp.vo.RegisterTeacherVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthMapper authMapper;

    @Autowired
    private AuthService authService;

    private JsonResult<AuthResponseVO> returnFunc(AuthResponseVO response) {
        if(response.getCode() == 0)
            return JsonResult.success(response, response.getMsg());
        else
            return JsonResult.error(1, response.getMsg());
    }

    @PostMapping("/login")
    public JsonResult<AuthResponseVO> login(@RequestBody LoginRequestVO request) {
        AuthResponseVO response = authService.loginByPassword(request);
        return returnFunc(response);
    }


    @GetMapping("/wechat/login/{openid}")
    public JsonResult<AuthResponseVO> wechatLogin(String openid) {
        AuthResponseVO response = authService.loginByOpenid(openid);
        return returnFunc(response);
    }

    @PostMapping("/register/student")
    public JsonResult<AuthResponseVO> registerStudent(@RequestBody RegisterStudentVO request) {
        AuthResponseVO response = authService.registerStudent(request);
        return returnFunc(response);
    }

    @PostMapping("/register/teacher")
    public JsonResult<AuthResponseVO> registerTeacher(@RequestBody RegisterTeacherVO request) {
        AuthResponseVO response = authService.registerTeacher(request);
        return returnFunc(response);
    }

}