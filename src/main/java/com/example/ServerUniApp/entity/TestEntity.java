package com.example.ServerUniApp.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "测试对象")
public class TestEntity {
    @Schema(description = "测试对象的id")
    private Integer id;
    @Schema(description = "测试对象的名字")
    private String name;
    @Schema(description = "测试对象的内容")
    private String content;

}
