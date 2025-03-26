package com.example.ServerUniApp.mapper;

import com.example.ServerUniApp.entity.Auth;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AuthMapper {

    @Select("select * from auth where id = #{id}")
    Auth findById(Integer id);

    @Select("SELECT * FROM auth WHERE user_number = #{userNumber}")
    Auth findByUserNumber(String userNumber);

    // 根据openid查询用户
    @Select("SELECT * FROM auth WHERE openid = #{openid}")
    Auth findByOpenid(String openid);

    // 更新Openid
    @Update("UPDATE auth SET openid = #{openid} WHERE id = #{id}")
    void updateOpenid(@Param("id") Integer id, @Param("openid") String openid);

    // 更新密码
    @Update("UPDATE auth SET password = #{password} WHERE id = #{id}")
    void updatePassword(@Param("id") Integer id, @Param("password") String password);

    // 注册用户
    @Insert("INSERT INTO auth (user_number, password, openid, role) VALUES (#{userNumber}, #{password}, #{openid}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id")  // 确保 id 自动填充
    void insertAuth(Auth auth);

}
