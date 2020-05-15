package com.TradingWebsite.Service;

import com.TradingWebsite.Model.Commodity;
import com.TradingWebsite.Model.User;

import java.util.List;


public interface UserService {

    User findByUsername(String name);

    void saveUserInfo(User user);

    User checkCode(String code);

    void updateUserStatus(User user);

    User findByUserEmail(String email);

    User login(String email, String password);

    User findUserInfoById(long id);

    String userPassword(long id);

    boolean updateUserPassword(String password,long id);

    Long findCountOfUser();

    List<User> findListUser();

    User findUserInfo(String email);

    List<User> findNameBy_Admin(String name);

}
