package com.TradingWebsite.Dao;

import com.TradingWebsite.Model.Collection;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CollectionDao {
    /**
     * 根据id查询商品
     * @param uid
     * @param cid
     * @return
     */
    @Select("select * from Collection where uid={uid} and cid=#{cid}")
    Collection findCollectionById(@Param("uid")long uid ,@Param("cid")long cid);

    /**
     * 添加商品进购物车
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
}
