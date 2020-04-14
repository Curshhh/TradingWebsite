package com.TradingWebsite.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 购物车表
 */
@Data
public class Cart {
    private  long sid;//购物车id
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private String modify;//修改时间
    private int status;//商品状态
    private long quantity;//商品数量
    private double total;//商品总价格
    private long cid;//商品id
    private long uid;//用户id

}
