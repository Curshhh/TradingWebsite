package com.TradingWebsite.Dao;

import com.TradingWebsite.Model.Commodity;
import com.TradingWebsite.Model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CommodityDao {

    /**
     * 查询所有商品
     * @return
     */
    @Select("select Commodity.*,User.email from Commodity LEFT JOIN User ON Commodity.uid=User.id")
     List<Commodity> findAllCommodity();

    @Select("select Commodity.*,User.email from Commodity LEFT JOIN User ON Commodity.uid=User.id where Commodity.name LIKE CONCAT('%',#{0},'%')")
    List<Commodity> findByNameOfCommodity_Admin(String name);
    /**
     * 模糊查询商品名称
     * @return
     */
    @Select("select * from Commodity where name LIKE CONCAT('%',#{0},'%')")
    List<Commodity> findByNameOfCommodity(String name);

    /**
     * 种类查询商品
     * @param sort
     * @return
     */
    @Select("select * from Commodity where sort=#{sort}")
    List<Commodity> findByTypeOfCommodity(String sort);

    /**
     * 查看商品详情
     * @param id
     * @return
     */
    @Select("select Commodity.*,User.email from Commodity LEFT JOIN User ON Commodity.uid=User.id where Commodity.id=#{id}")
    Commodity findCommodityInfoById(long id);

    /**
     * 添加商品信息
     * @param commodity
     */
    @Insert("insert into Commodity(name,level,details,price,sort,count,transaction,sales,modify,image,uid,status) " +
            "value(#{name},#{level},#{details},#{price},#{sort},#{count},#{transaction},#{sales},#{modify},#{image},#{uid},#{status})")
    boolean addCommodityInfo(Commodity commodity);

    /**
     * 修改商品信息
     * @param commodity
     * @return
     */
    @Update("update Commodity set name=#{name},level=#{level},details=#{details},price=#{price},sort=#{sort},count=#{count},transaction=#{transaction}," +
            "sales=#{sales},modify=#{modify},image=#{image},uid=#{uid},status=#{status} where id=#{id}")
    void updateCommodityInfo(Commodity commodity);

    /**
     * 卖家删除商品
     * @param id
     * @return
     */
    @Delete("delete from Commodity where id=#{id}")
    boolean deleteCommodityById(long id);

    /**
     * 查询商品数量
     * @param id
     * @return
     */
    @Select("select count from Commodity where id=#{id}")
    long findCountById(long id);

    /**
     * 查询最新发布的商品
     * @return
     */
    @Select("SELECT * from Commodity where count>=1 ORDER BY modify DESC LIMIT 0,5 ")
    List<Commodity> findNewCommodityByModify();

    /**
     * 查询最便宜的商品
     * @return
     */
    @Select("SELECT * from Commodity where count>=1 ORDER BY price ASC LIMIT 0,5 ")
    List<Commodity> findPriceLowCommodityByPrice();

    @Select("select name from Commodity where name=#{name}")
    Commodity findCommodityByNameForEquals(String name);

    /**
     * 查询价格最低的物品
     * @param sort
     * @return
     */
    @Select("SELECT *from Commodity where count>=1 and sort=#{sort}  ORDER BY modify DESC LIMIT 0,5")
    List<Commodity> findSortNewTimeWares(String sort);

    /**
     * 根据商品名查询商品信息
     * @param name
     * @return
     */
    @Select("select * from Commodity where name=#{name}")
    Commodity findCommodityByName(String name);

    /**
     * 根据用户id查询物品表
     * @param uid
     * @return List
     */
    @Select("select * from Commodity where uid=#{uid}")
    List<Commodity> findCommodityByUserUid(long uid);

    /**
     * 修改商品信息，下架商品
     * @param id
     * @return
     */
    @Update("UPDATE Commodity set status=0,count=0 WHERE id=#{id}")
    boolean updateCommodityInfoById(long id);


    /*----------------------管理员---------------------------------*/
    /**
     * 管理员查询当前商品总数量
     * @return
     */
    @Select("select SUM(count) from Commodity")
    Long findCountOfCommodity();
    /**
     * 管理员查看当前商品销量
     * @return
     */
    @Select("SELECT SUM(sales) from Commodity where sales<>0")
    Long findCountSalesOfCommodity();


}
