package com.TradingWebsite.Service;

import com.TradingWebsite.Dao.CollectionDao;
import com.TradingWebsite.Model.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionServiceImpl implements  CollectionService {
    @Autowired
    private CollectionDao collectionDao;

    /**
     * 查询用户收藏
     * @param uid
     * @param cid
     * @return
     */
    @Override
    public Collection findCollectionById(long uid, long cid) {
       try {
           Collection collection=this.collectionDao.findCollectionById(uid, cid);
           return collection;
       }catch (Exception e){
           System.out.println("出错");
           return null;
       }
    }

    /**
     * 添加用户收藏
     * @param collection
     */
    @Override
    public void insertCollection(Collection collection) {
        collectionDao.insertCollection(collection);
    }

    /**
     * 根据uid查询collection
     * @param uid
     * @return
     */
    @Override
    public List<Long> findCidOfcollectionByUid(long uid) {
        try {
            List<Long> collectionList=collectionDao.findCidOfCollectionByUid(uid);
            return collectionList;

        }catch (Exception e){
            return null;
        }
    }

    /**
     * 删除收藏夹物品
     * @param tid
     * @return
     */
    @Override
    public boolean deleteCollect(long tid) {
        try {
            this.collectionDao.deleteCollect(tid);
            return true;
        } catch (Exception e) {
           return false;
        }

    }
}
