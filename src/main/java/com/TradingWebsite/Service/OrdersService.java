package com.TradingWebsite.Service;

import com.TradingWebsite.Model.Orders;

public interface OrdersService {
    boolean insertOrdersInfo(Orders orders);
    double countPriceOfOrders(long uid);
//    long selectNmuberOreders(long uid);

}
