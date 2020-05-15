package com.TradingWebsite.Service;

import com.TradingWebsite.Dao.PublishDao;
import com.TradingWebsite.Model.Publish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PublishServiceImpl implements publishService {

    @Autowired
    private PublishDao publishDao;
    @Override

    public boolean insertMyPublish(Publish publish) {
//        try {
            publishDao.insertMyPublish(publish);
            return true;
//        } catch (Exception e) {
//            return false;
//        }
    }

    @Override
    public List<Map> findSellerOrders(long uid) {
        return  publishDao.findSellerOrders(uid);
    }
}
