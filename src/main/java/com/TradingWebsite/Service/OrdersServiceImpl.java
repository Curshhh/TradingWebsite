package com.TradingWebsite.Service;

import com.TradingWebsite.Dao.OrdersDao;
import com.TradingWebsite.Model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersDao ordersDao;

    @Override
    public boolean insertOrdersInfo(Orders orders) {
//        try {
            ordersDao.insertOrdersInfo(orders);
            return true;
//        } catch (Exception e) {
//            return false;
//        }
    }

    @Override
    public double countPriceOfOrders(long uid) {
        return ordersDao.countPriceOfOrders(uid);
    }

    /**
     * 查询用户订单量
     * @param uid
     * @return
     */
    @Override
    public long selectNmuberOreders(long uid) {
        return ordersDao.selectNmuberOreders(uid);
    }
    /**
     * 查询用户订单信息
     * @param uid
     * @return
     */
    @Override
    public List<Map> findOrdersInfoByUid(long uid) {
        List<Map> orders=null;
        orders=ordersDao.findOrdersInfoByUid(uid);
        return orders;
    }

    @Override
    public Map findOrdersAndCommodityInfoByOid(long oid) {
        return  ordersDao.findOrdersAndCommodityInfoByOid(oid);
    }

    /**
     * 更改订单状态
     * @param cid
     * @param uid
     * @return
     */
    @Override
    public boolean updateOrdersStatus_1ByCidAndUid(long cid, long uid) {
        return ordersDao.updateOrdersStatus_1ByCidAndUid(cid,uid);
    }

    @Override
    public boolean updateOrdersStatus_2ByCidAndUid(long cid, long uid) {
        return ordersDao.updateOrdersStatus_2ByCidAndUid(cid,uid);
    }

    @Override
    public boolean updateOrdersStatus_3ByCidAndUid(long cid, long uid) {
        return ordersDao.updateOrdersStatus_3ByCidAndUid(cid,uid);
    }

    /*----------------------管理员---------------------------------*/
    @Override
    public Long findCountEndOfOrders() {
        return ordersDao.findCountEndOfOrders();
    }

    @Override
    public String findSumPriceOfOrders() {
        return ordersDao.findSumPriceOfOrders();
    }
}
