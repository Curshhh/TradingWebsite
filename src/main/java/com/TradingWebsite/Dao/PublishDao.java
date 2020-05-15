package com.TradingWebsite.Dao;

import com.TradingWebsite.Model.Publish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface PublishDao {
    /**
     * 添加用户发布记录
     * @param publish
     * @return
     */
    @Insert("insert into Publish(uid,cid,name,price,quantity,modify) value(#{uid},#{cid},#{name},#{price},#{quantity},#{modify})")
    boolean insertMyPublish(Publish publish);

    /**
     * 查询卖家订单信息
     * @param uid
     * @return
     */
    @Select("SELECT Orders.oid,Orders.cid,Orders.modify,Orders.quantity,Orders.price,Orders.uid AS buyer,Publish.uid AS seller,Orders.status FROM Orders LEFT JOIN Publish ON Orders.cid=Publish.cid WHERE Publish.uid=#{uid}")
    List<Map> findSellerOrders(long uid);
}
