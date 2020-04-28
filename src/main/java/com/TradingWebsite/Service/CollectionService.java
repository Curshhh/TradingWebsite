package com.TradingWebsite.Service;

import com.TradingWebsite.Model.Collection;

import java.util.List;
import java.util.Map;

public interface CollectionService {

    Collection findCollectionById(long uid, long cid);

    void insertCollection(Collection collection);

    List<Long> findCidOfcollectionByUid(long uid);

    boolean deleteCollect(long tid);

    List<Map> findCollectionInfoByUid(long uid);
}
