package com.TradingWebsite.Dao;

import com.TradingWebsite.Model.Orders;
import org.apache.ibatis.annotations.*;
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
    @Select("SELECT Orders.oid,Orders.cid,Orders.uid,Orders.modify,Orders.price,Orders.status from Orders WHERE uid=#{uid}")
    List<Map> findOrdersInfoByUid(long uid);

    @Select("SELECT Orders.modify,Orders.cid,Orders.oid,Commodity.name,Commodity.image,Orders.quantity,Commodity.price AS price,Commodity.transaction,Orders.price AS total,User.address FROM (Commodity LEFT JOIN Orders ON Commodity.id=Orders.cid) LEFT JOIN User ON User.id=Orders.uid WHERE Orders.oid=#{oid}")
    Map findOrdersAndCommodityInfoByOid(long oid);

    /**
     * 更改订单状态
     * @param cid
     * @param uid
     * @return
     */
    @Update("update Orders set status=1 where cid=#{cid} and uid=#{uid}")
    boolean updateOrdersStatus_1ByCidAndUid(@Param("cid")long cid,@Param("uid")long uid);
    @Update("update Orders set status=2 where cid=#{cid} and uid=#{uid}")
    boolean updateOrdersStatus_2ByCidAndUid(@Param("cid")long cid,@Param("uid")long uid);
    @Update("update Orders set status=3 where cid=#{cid} and uid=#{uid}")
    boolean updateOrdersStatus_3ByCidAndUid(@Param("cid")long cid,@Param("uid")long uid);

    /*----------------------管理员---------------------------------*/

    /**
     * 管理员查看当前交易完成的订单量
     * @return
     */
    @Select("SELECT COUNT(*) from Orders where status=3")
    Long findCountEndOfOrders();

    /**
     * 查看交易完成总金额
     * @return
     */
    @Select("SELECT SUM(price) from Orders where status=3")
    String findSumPriceOfOrders();
}
