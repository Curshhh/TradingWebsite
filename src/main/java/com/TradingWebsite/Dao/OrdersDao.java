package com.TradingWebsite.Dao;

import com.TradingWebsite.Model.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface OrdersDao {
    /**
     * 创建订单
     * @param orders
     * @return
     */
    @Insert("insert to Orders(modify,status,quantity,cid,uid)value(#{modify},#{status},#{quantity},#{cid},#{uid}) ")
    boolean insertOrdersInfo(Orders orders);

    /**
     * 计算订单总价钱
     * @param uid
     * @return
     */
    @Select("select SUM(price) from Orders where uid=#{uid})")
    double countPriceOfOrders(long uid);

    @Select("select count(*) from Orders where uid=#{uid} and Orders.status=0")
    long selectNmuberOreders(long uid);

}
