package com.TradingWebsite.Model;

import lombok.Data;

@Data
public class Orders {
    private long oid;//订单id
    private String modify;//时间
    private long quantity;//数量
    private long status;//状态
    private double price;//价格
    private long cid;//商品id
    private long uid;//用户id

}
