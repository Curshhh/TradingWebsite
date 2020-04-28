package com.TradingWebsite.Service;

import com.TradingWebsite.Dao.UserDao;
import com.TradingWebsite.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 根据用户名查询用户信息
     * @param name
     * @return
     */
    @Override
    public User findByUsername(String name) {
        User user=null;
        try {
            user=userDao.findByUsername(name);
        } catch (Exception e) {

        }
        return user;
    }

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @Override
    public User findUserInfoById(long id) {
        return userDao.findUserInfoById(id);
    }

    /**
     * 保存用户
     * @param user
     * @return
     */
    @Override
    public void saveUserInfo(User user) {

        userDao.saveUserInfo(user);
    }

    /**
     * 用户激活码匹配
     * @param code
     * @return
     */
    @Override
    public User checkCode(String code) {
       User user=userDao.checkCode(code);
       return user;
    }

    /**
     * 修改用户账号状态
     * @param user
     */
    @Override
    public void updateUserStatus(User user) {

            this.userDao.updateUserStatus(user);

    }

    /**
     * 根据用户emamil查询用户
     * @param email
     * @return
     */
    @Override
    public User findByUserEmail(String email) {
        User user=userDao.findByUserEmail(email);
        return user;
    }

    /**
     * 根据邮箱和密码查询用户信息
     * @param email
     * @param password
     * @return
     */
    public User login(String email,String password){
        User user=userDao.login(email,password);
        if (user!=null){
            user.getPassword().equals(password);
            return user;
        }
        return null;
    }

    @Override
    public String userPassword(long id) {
        return userDao.userPassword(id);
    }

    @Override
    public boolean updateUserPassword(String password,long id) {

            userDao.updateUserPassword(password,id);
            return true;

           // System.out.println("updateUserPassword修改出错");

        }


}
