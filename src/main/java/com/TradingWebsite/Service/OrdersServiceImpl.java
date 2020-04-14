package com.TradingWebsite.Service;

import com.TradingWebsite.Dao.OrdersDao;
import com.TradingWebsite.Model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersDao ordersDao;

    @Override
    public boolean insertOrdersInfo(Orders orders) {
        try {
            ordersDao.insertOrdersInfo(orders);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public double countPriceOfOrders(long uid) {
        return ordersDao.countPriceOfOrders(uid);
    }
}
