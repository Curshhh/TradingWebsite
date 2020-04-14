package com.TradingWebsite.Service;

import com.TradingWebsite.Dao.CommodityDao;
import com.TradingWebsite.Model.Commodity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    private CommodityDao commodityDao;

    /**
     * 查询所有商品
     * @return
     */
    @Override
    public List<Commodity> findAllCommodity() {

        return this.commodityDao.findAllCommodity();
    }

    /**
     * 模糊查询商品名
     * @param name
     * @return
     */
    @Override
    public List<Commodity> findByNameOfCommodity(String name) {
        return this.commodityDao.findByNameOfCommodity(name);
    }

    /**
     * 根据种类查询商品
     * @param sort
     * @return
     */
    @Override
    public List<Commodity> findByTypeOfCommodity(String sort) {
      List<Commodity> commodityList=this.commodityDao.findByTypeOfCommodity(sort);
      if (commodityList!=null){
          return commodityList;
      }else return null;

    }

    /**
     * 查看商品详情
     * @param id
     * @return
     */
    @Override
    public Commodity findCommodityInfoById(long id) {
        Commodity commodity=commodityDao.findCommodityInfoById(id);
        if (commodity!=null){
            return commodity;
        }else
            return null;
    }

    /**
     * 用户添加商品
     * @param commodity
     */
    @Override
    public boolean addCommodityInfo(Commodity commodity) {
        try {
            commodityDao.addCommodityInfo(commodity);
            return true;
        }catch (Exception e){
            System.out.println("sql出错");
            return false;
        }
    }

    /**
     * 修改商品信息
     * @param commodity
     * @return
     */
    @Override
    public void updateCommodityInfo(Commodity commodity) {
        try {
            this.commodityDao.updateCommodityInfo(commodity);
        }catch (Exception e){
            System.out.println("sql出错");
        }
    }

    /**
     * 卖家删除物品
     * @param id
     * @return
     */
    @Override
    public Boolean deleteCommodityById(long id) {
        try {
            this.commodityDao.deleteCommodityById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 查询商品数量
     * @param id
     * @return
     */
    @Override
    public long findCountById(long id) {
      return commodityDao.findCountById(id);

    }

    /**
     * 查询最新的4个发布商品
     * @return
     */
    @Override
    public List<Commodity> findNewCommodityByModify() {
        try {
            return commodityDao.findNewCommodityByModify();
        } catch (Exception e) {
            System.out.println("查询出错");
            return null;
        }
    }
    /**
     * 查询价格最低的4个发布商品
     * @return
     */
    @Override
    public List<Commodity> findPriceLowCommodityByPrice() {
        try {
            return commodityDao.findPriceLowCommodityByPrice();
        } catch (Exception e) {
            System.out.println("查询出错");
            return null;
        }
    }

    /**
     * 跟据完整商品名查找商品信息
     * @param name
     * @return
     */
    @Override
    public Commodity findCommodityByNameForEquals(String name) {

        return commodityDao.findCommodityByNameForEquals(name);
    }
}
