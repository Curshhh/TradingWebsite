package com.TradingWebsite.Service;

import com.TradingWebsite.Model.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrdersService {
    boolean insertOrdersInfo(Orders orders);
    double countPriceOfOrders(long uid);

    long selectNmuberOreders(long uid);

     List<Map> findOrdersInfoByUid(long uid);

    Map findOrdersAndCommodityInfoByOid(long oid);
    boolean updateOrdersStatus_1ByCidAndUid(@Param("cid")long cid, @Param("uid")long uid);
    boolean updateOrdersStatus_2ByCidAndUid(@Param("cid")long cid,@Param("uid")long uid);
    boolean updateOrdersStatus_3ByCidAndUid(@Param("cid")long cid,@Param("uid")long uid);

    Long findCountEndOfOrders();
    String findSumPriceOfOrders();
}
