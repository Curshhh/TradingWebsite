package com.TradingWebsite.Dao;

import com.TradingWebsite.Model.Publish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface PublishDao {
    /**
     * 用户查看自身的发布记录
     * @param publish
     * @return
     */
    @Insert("insert into Publish(uid,name,price,quantity,modify) value(#{uid},#{name},#{price},#{quantity},#{modify})")
    boolean insertMyPublish(Publish publish);
}
