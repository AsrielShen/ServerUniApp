package com.example.ServerUniApp.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
//@Data
//@AllArgsConstructor
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    // 生成 Token
    public String getToken(Integer userId, String role) {
        return JWT.create()
                .withSubject(String.valueOf(userId)) // 存储 userId
                .withClaim("role", role)  // 身份
                .withIssuedAt(new Date())  // 签发时间
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime)) // 过期时间
                .sign(Algorithm.HMAC256(secretKey));  // 生成签名
    }

    // 认证信息
    public Map<String, Object> getUserInfo(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secretKey))
                    .build()
                    .verify(token);
            //从token中获得id和role
            Integer userId = Integer.parseInt(jwt.getSubject());
            String role = jwt.getClaim("role").asString();

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userId", userId);
            userInfo.put("role", role);

            return userInfo;
        } catch (Exception e) {
            // Token 失效或错误
            return null;
        }
    }

    // 检查 Token 是否有效
    public boolean isValid(String token) {
        return getUserInfo(token) != null;
    }

}