package com.TradingWebsite.Service;

import com.TradingWebsite.Dao.PublishDao;
import com.TradingWebsite.Model.Publish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublishServiceImpl implements publishService {

    @Autowired
    private PublishDao publishDao;
    @Override

    public boolean insertMyPublish(Publish publish) {
        try {
            publishDao.insertMyPublish(publish);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
