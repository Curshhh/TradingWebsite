package com.TradingWebsite.Service;

import com.TradingWebsite.Model.Manager;


public interface ManagerService {

    Manager adminLogin(String email,String password);
}
