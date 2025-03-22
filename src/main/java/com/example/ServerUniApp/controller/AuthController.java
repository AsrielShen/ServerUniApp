package com.example.ServerUniApp.controller;


import com.example.ServerUniApp.common.JsonResult;
import com.example.ServerUniApp.service.AuthService;
import com.example.ServerUniApp.vo.AuthResponseVO;
import com.example.ServerUniApp.vo.LoginRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public JsonResult<AuthResponseVO> login(@RequestBody LoginRequestVO request) {
        AuthResponseVO response = authService.loginByPassword(request);
        if(response != null)
            return JsonResult.success(response, "登录成功");
        else return JsonResult.error("账号或密码出错！");
    }


    @PostMapping("/wechat/login")
    public JsonResult<AuthResponseVO> wechatLogin(@RequestBody String openid) {
        AuthResponseVO response = authService.loginByOpenid(openid);
        if(response != null)
            return JsonResult.success(response, "登录成功");
        else return JsonResult.error("账号或密码出错！");
    }

    @PostMapping("/register/student")
    public JsonResult<AuthResponseVO> registerStudent(@RequestBody String openid) {
        return JsonResult.success();
    }

}

