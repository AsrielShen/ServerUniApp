package com.example.ServerUniApp.mapper;

import com.example.ServerUniApp.entity.Auth;
import org.apache.ibatis.annotations.*;


@Mapper
public interface AuthMapper {

    // 根据用户名查询用户
    @Select("SELECT * FROM auth WHERE user_number = #{userNumber}")
    Auth selectByUserNumber(String userNumber);

    // 根据openid查询用户
    @Select("SELECT * FROM auth WHERE openid = #{openid}")
    Auth selectByOpenid(String openid);

    @Update("UPDATE auth SET openid = #{openid} WHERE id = #{id}")
    void updateOpenid(@Param("id") Integer id, @Param("openid") String openid);

    // 注册用户
    @Insert("INSERT INTO auth (user_number, password, role) VALUES (#{userNumber}, #{password}, #{role})")
    void insertAuth(Auth auth);
}
