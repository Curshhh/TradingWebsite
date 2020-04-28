package com.TradingWebsite.Dao;

import com.TradingWebsite.Model.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface OrdersDao {
    /**
     * 创建订单
     * @param orders
     * @return
     */
    @Insert("insert into Orders(modify,status,quantity,price,cid,uid) value(#{modify},#{status},#{quantity},#{price},#{cid},#{uid})")
    boolean insertOrdersInfo(Orders orders);

    /**
     * 计算订单总价钱
     * @param uid
     * @return
     */
    @Select("select SUM(price) from Orders where uid=#{uid})")
    double countPriceOfOrders(long uid);

    /**
     * 订单数量
     * @param uid
     * @return
     */
    @Select("select count(*) from Orders where uid=#{uid}")
    long selectNmuberOreders(long uid);

    /**
     * 查询用户订单信息
     * @param uid
     * @return
     */
    @Select("SELECT Orders.oid,Orders.modify,Orders.price,Orders.status from Orders WHERE uid=#{uid}")
    List<Map> findOrdersInfoByUid(long uid);
    @Select("SELECT Orders.modify,Orders.cid,Orders.oid,Commodity.name,Commodity.image,Orders.quantity,Commodity.price AS price,Commodity.transaction,Orders.price AS total,User.address FROM (Commodity LEFT JOIN Orders ON Commodity.id=Orders.cid) LEFT JOIN User ON User.id=Orders.uid WHERE Orders.oid=#{oid}")
    Map findOrdersAndCommodityInfoByOid(long oid);
}
