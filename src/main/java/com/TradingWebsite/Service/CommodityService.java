package com.TradingWebsite.Service;

import com.TradingWebsite.Model.Commodity;

import java.util.List;

public interface CommodityService {

    List<Commodity> findAllCommodity();//查询全部商品

    List<Commodity> findByNameOfCommodity(String name);//根据商品名模糊查询商品

    List<Commodity> findByTypeOfCommodity(String sort);//分种类查询商品

    Commodity findCommodityInfoById(long id);//根据商品id寻找商品信息

    boolean addCommodityInfo(Commodity commodity);//用户添加商品

    void updateCommodityInfo(Commodity commodity);//修改商品信息

    Boolean deleteCommodityById(long id);//用户删除商品

    long findCountById(long id);//根据id查询商品库存

    List<Commodity> findNewCommodityByModify();//查询最新的4个发布商品

    List<Commodity> findPriceLowCommodityByPrice();

    Commodity findCommodityByNameForEquals(String name);

    List<Commodity> findSortNewTimeWares(String sort);


}