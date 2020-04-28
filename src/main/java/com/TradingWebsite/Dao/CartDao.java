package com.TradingWebsite.Dao;

import com.TradingWebsite.Model.Cart;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
//mapper的作用是可以给mapper接口自动生成一个实现类，让spring对mapper接口的bean进行管理，并且可以省略去写复杂的xml文件
//@Component把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>
public interface CartDao {
    /**
     *添加商品进入购物车
     * @return
     */
    @Insert("insert into Cart(modify,status,quantity,total,cid,uid) value(#{modify},#{status},#{quantity},#{total},#{cid},#{uid})")
    boolean insertCommodityIntoCart(Cart cart);

    /**
     *根据用户和商品id查询购物车信息
     * @param uid
     * @param cid
     * @return
     */
    @Select("select * from Cart where uid=#{uid} and cid=#{cid}")
    Cart findCartInfoByid(@Param("uid")long uid,@Param("cid")long cid);

    /**
     * 根据商品id修改购物车商品信息
     * @param cart
     */
    @Update("update Cart set modify=#{modify},status=#{status},quantity=#{quantity},total=#{total} where cid=#{cid}")
    void updateCartInfoByCid(Cart cart);

    /**
     * 清空购物车
     * @param uid
     * @return
     */
    @Delete("delete from Cart where uid=#{uid}")
    boolean deleteAllCartByUid(long uid);

    /**
     * 删除购物车单个商品
     * @param sid
     * @return
     */
    @Delete("delete from Cart where sid=#{sid} and uid=#{uid}")
    boolean deleteCartInfoBySid(@Param("sid")long sid,@Param("uid")long uid);

    /**
     * 查看购物车
     * @param uid
     * @return
     */
    @Select("SELECT Cart.sid,Commodity.id,Commodity.name,Commodity.price,Cart.modify,Cart.quantity,Commodity.count,Commodity.image,Cart.total FROM Commodity RIGHT JOIN Cart ON Commodity.id = Cart.cid and Cart.uid=#{uid}")
    List<Map> selectCartListByUser(long uid);

    /**
     * 查购物车商品总价
     * @param uid
     * @return
     */
    @Select("select SUM(total) from Cart where uid=#{uid}")
    double selectCartPrices(long uid);

    /**
     * 使用uid查询用户购物车中的商品id
     * @param uid
     * @return
     */
    @Select("select cid from Cart where uid=#{uid}")
    List<Long> findCidByUidFromCart(long uid);

    /**
     * 根据cid查询购物车物品信息
     * @param cid
     * @return
     */
    @Select("select * from Cart where cid=#{cid}")
    Cart findCartInfoByCid(long cid);

    /**
     * 根据uid统计用户购物车物品数量
     * @param uid
     * @return
     */
    @Select("select count(sid) from Cart where uid=#{uid}")
    Long findUserCartNumber(long uid);

    @Select("select sum(quantity) from Cart where uid=#{uid}")
    Long findUserCartQuantity(long uid);

    @Select("select * from Cart where sid=#{sid}")
    Cart findCartInfoBySid(long sid);
}
