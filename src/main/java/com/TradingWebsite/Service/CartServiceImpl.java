package com.TradingWebsite.Service;

import com.TradingWebsite.Dao.CartDao;
import com.TradingWebsite.Model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
//注入dao
public class CartServiceImpl implements  CartService {
    @Autowired
    private CartDao cartDao;

    /**
     * 加入购物车
     * @param cart
     * @return
     */
    @Override
    public boolean insertCommodityIntoCart(Cart cart) {
        try {
            cartDao.insertCommodityIntoCart(cart);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 找购物车信息
     * @param uid
     * @param cid
     * @return
     */
    @Override
    public Cart findCartInfoById(long uid, long cid) {
        try {
            return cartDao.findCartInfoByid(uid,cid);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 修改购物车的信息
     * @param cart
     * @return
     */
    @Override
    public boolean updateCartInfoByCid(Cart cart) {
        try {
        cartDao.updateCartInfoByCid(cart);
        return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 清空购物车
     * @param uid
     * @return
     */
    @Override
    public boolean deleteAllCartByUid(long uid) {
        try {
            cartDao.deleteAllCartByUid(uid);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 删除购物车内单个商品
     * @param sid
     * @param uid
     * @return
     */
    @Override
    public boolean deleteCartInfoBySid(long sid,long uid) {
        try {
            cartDao.deleteCartInfoBySid(sid,uid);
            return true;
        } catch (Exception e) {
            return  false;
        }
    }

    /**
     * 查购物车清单
     * @param uid
     * @return
     */
    @Override
    public List<Map> selectCartListByUser(long uid) {
        try {
            return cartDao.selectCartListByUser(uid);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 查购物车总价
     * @param uid
     * @return
     */
    @Override
    public double selectCartPrices(long uid) {
        return cartDao.selectCartPrices(uid);
    }

    @Override
    public List<Long> findCidByUidFromCart(long uid) {
        return cartDao.findCidByUidFromCart(uid);
    }

    @Override
    public Cart findCartInfoByCid(long cid) {
        return cartDao.findCartInfoByCid(cid);
    }
}
