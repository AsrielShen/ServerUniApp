package com.example.ServerUniApp.controller;


import com.example.ServerUniApp.common.JsonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/generate")
    public JsonResult<?> generateFromLLM(@RequestParam String prompt) {
        try {
            // 拼接FastAPI的完整URL
            String url = "http://localhost:8000/generate?prompt=" + prompt;

            // 发送GET请求到FastAPI
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            // 解析FastAPI返回的JSON，提取 generated_text
            String responseBody = response.getBody();
            String generatedText = objectMapper.readTree(responseBody).get("generated_text").asText();

            // 返回成功
            return JsonResult.success(generatedText, "生成成功");
        } catch (Exception e) {
            e.printStackTrace();
            // 返回错误信息
            return JsonResult.error("生成失败: " + e.getMessage());
        }
    }

}
