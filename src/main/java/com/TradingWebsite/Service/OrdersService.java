package com.TradingWebsite.Service;

import com.TradingWebsite.Model.Orders;

import java.util.List;
import java.util.Map;

public interface OrdersService {
    boolean insertOrdersInfo(Orders orders);
    double countPriceOfOrders(long uid);

    long selectNmuberOreders(long uid);

     List<Map> findOrdersInfoByUid(long uid);

    Map findOrdersAndCommodityInfoByOid(long oid);
}
