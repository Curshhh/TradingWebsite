package com.TradingWebsite.Dao;

import com.TradingWebsite.Model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserDao {
    /**
     * 根据用户名查询用户信息
     * @param name
     * @return
     */
    @Select("select * from User where name=#{name} ")
     User findByUsername(String name);
    /**
     * 根据邮箱查询用户邮箱是否注册
     * @param email
     * @return
     */
    @Select("select * from User where email=#{email}")
    User findByUserEmail(String email);
    /**
     * 用户保存
     * @param user
     */
    @Insert("insert into User(name,password,sex,birthday,phone,email,address,status,code,power,modify) " +
            "value(#{name},#{password},#{sex},#{birthday},#{phone},#{email},#{address},#{status},#{code},#{power},#{modify})")
     void saveUserInfo(User user);

    /**
     * 根据用户激活码查询用户
     * @param code
     * @return
     */
    @Select("select * from User where code=#{code}")
    User checkCode(String code);
    @Select("update")
    /**
     * 修改用户信息
     * @param user
     */
    @Update("update User set name=#{name},password=#{password},sex=#{sex},birthday=#{birthday},phone=#{phone},email=#{email},address=#{address},status=#{status},code=#{code},power=#{power},modify=#{modify} where id=#{id}")
    void updateUserStatus(User user);

    /**
     * 根据邮箱和密码查询用户信息
     * @param email
     * @param password
     * @return
     */
    @Select("select * from User where email=#{email} and password=#{password}")
    User login(@Param("email")String email,@Param("password")String password);

    @Select("select * from User where id=#{id}")
    User findUserInfoById(long id);

    @Select("select password from User where id=#{id}")
    String userPassword(long id);

    /**
     * 修改密码
     * @param password
     * @param id
     * @return
     */
    @Update("update User set password=#{password} where id=#{id}")
    boolean updateUserPassword(@Param("password")String password,@Param("id")long id);
}
