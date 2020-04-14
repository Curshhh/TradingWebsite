package com.TradingWebsite.Dao;

import com.TradingWebsite.Model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MessageDao {

    @Insert("insert into Message(content,modify,cid,uid) value(#{content},#{modify},#{cid},#{uid})")
    boolean insertMessageInfo(Message message);

    @Select("select * from Message where cid=#{cid}")
    List<Message> findMessageListWithCommodity(long cid);
}
