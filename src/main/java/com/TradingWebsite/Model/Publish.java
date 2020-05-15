package com.TradingWebsite.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Publish {
    private long id;//发布id
    private long uid;//用户id
    private long cid;//商品id
    private String name;//商品名
    private double price;//商品价格
    private long quantity;//商品数量
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private String modify;//发布时间;
}
