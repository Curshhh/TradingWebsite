package com.TradingWebsite.Model;

import lombok.Data;

@Data
public class Publish {
    private long id;//发布id
    private long uid;//用户id
    private String name;//商品名
    private double price;//商品价格
    private long quantity;//商品数量
    private String modify;//发布时间;
}
