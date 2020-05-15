package com.TradingWebsite.Dao;

import com.TradingWebsite.Model.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ManagerDao {
    /**
     * 管理员登陆
     * @param email
     * @param password
     * @return
     */
    @Select("select * from Manager where email=#{email} and password=#{password}")
    Manager adminLogin(@Param("email")String email, @Param("password")String password);


}
