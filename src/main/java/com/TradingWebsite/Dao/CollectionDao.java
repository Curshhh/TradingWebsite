package com.TradingWebsite.Dao;

import com.TradingWebsite.Model.Collection;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface CollectionDao {
    /**
     * 根据id查询商品
     * @param uid
     * @param cid
     * @return
     */
    @Select("select * from Collection where uid=#{uid} and cid=#{cid}")
    Collection findCollectionById(@Param("uid")long uid ,@Param("cid")long cid);

    /**
     * 添加商品进收藏栏
     * @param collection
     */
    @Insert("insert into Collection(modify,uid,cid) value(#{modify},#{uid},#{cid})")
    void insertCollection(Collection collection);

    /**
     * 根据uid查询collection
     * @param uid
     * @return
     */
    @Select("select cid from Collection where uid=#{uid}")
    List<Long> findCidOfCollectionByUid(long uid);

    /**
     * 删除收藏物品
     * @param tid
     * @return
     */
    @Delete("delete from Collection where tid=#{tid}")
    boolean deleteCollect(long tid);

    /**
     * 查看用户收藏夹内容
     * @param uid
     * @return
     */
    @Select("SELECT Collection.tid,Commodity.image,Commodity.name,Commodity.id,Commodity.price,Collection.modify from Commodity LEFT JOIN Collection ON Commodity.id=Collection.cid WHERE Collection.uid=#{uid}")
    List<Map> findCollectionInfoByUid(long uid);
}
