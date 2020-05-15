package com.TradingWebsite.Service;

import com.TradingWebsite.Model.Publish;

import java.util.List;
import java.util.Map;

public interface publishService {
    boolean insertMyPublish(Publish publish);
    List<Map> findSellerOrders(long uid);
}
