package com.TradingWebsite.Service;

import com.TradingWebsite.Dao.ManagerDao;
import com.TradingWebsite.Model.Manager;
import com.TradingWebsite.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    ManagerDao managerDao;

    /**
     * 管理登陆
     * @param email
     * @param password
     * @return
     */
    @Override
    public Manager adminLogin(String email, String password) {
       Manager manager=managerDao.adminLogin(email,password);
       if (manager!=null){
           manager.getPassword().equals(password);
           return manager;
       }
       return null;
    }
}
