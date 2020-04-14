package com.TradingWebsite.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Commodity {
    private long id;
    private String name;//商品名称
    private String level;//商品成色
    private String details;//详细信息
    private double price;//商品价钱
    private String sort;//分类
    private long count;//数量
    private String transaction;//交易方式
    private long sales;//销售量
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private String modify;//上架时间
    private String image;//商品照片
    private long uid;//卖家id
    private int status;//商品状态
}
