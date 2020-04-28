package com.TradingWebsite.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Orders {
    private long oid;//订单id
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private String modify;//时间
    private long quantity;//数量
    private long status;//状态
    private double price;//价格
    private long cid;//商品id
    private long uid;//用户id

}
