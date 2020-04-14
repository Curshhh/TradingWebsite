package com.TradingWebsite.Service;

import com.TradingWebsite.Model.Collection;

import java.util.List;

public interface CollectionService {

    Collection findCollectionById(long uid, long cid);

    void insertCollection(Collection collection);

    List<Long> findCidOfcollectionByUid(long uid);

    boolean deleteCollect(long tid);
}
