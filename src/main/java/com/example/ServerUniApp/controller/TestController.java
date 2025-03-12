package com.example.ServerUniApp.controller;

import com.example.ServerUniApp.common.JsonResult;
import com.example.ServerUniApp.entity.TestEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    @PathVariable 用于获得get请求的url参数，/{param}
    @RequestParam 用于获得所有请求的参数
    @RequestBody 用于获得非get请求的实体对象
 */


@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/info/{id}")
    @Operation(summary = "测试接口", description = "这是一个测试接口，返回一个测试对象")
    public JsonResult<TestEntity> getUserInfo(@PathVariable @Parameter(description = "获得的id", required = true)  Integer id) {
        TestEntity test = new TestEntity(id,"名字", "内容");
        return JsonResult.success(test);
    }

}
