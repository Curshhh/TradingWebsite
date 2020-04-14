package com.TradingWebsite.Service;

import com.TradingWebsite.Model.Cart;

import java.util.List;
import java.util.Map;

public interface CartService {
    boolean insertCommodityIntoCart(Cart cart);//添加商品进入购物车
    Cart findCartInfoById(long uid,long cid);
    boolean updateCartInfoByCid(Cart cart);
    boolean deleteAllCartByUid(long uid);
    boolean deleteCartInfoBySid(long sid,long uid);
    List<Map> selectCartListByUser(long uid);
    double selectCartPrices(long uid);
    List<Long> findCidByUidFromCart(long uid);
    Cart findCartInfoByCid(long cid);
}
