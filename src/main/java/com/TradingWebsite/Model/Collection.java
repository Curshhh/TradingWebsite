package com.TradingWebsite.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 收藏表
 */
@Data
public class Collection {
    private long tid;//收藏商品id
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private  String modify;
    private long cid;//商品id;
    private  long uid;//用户id;
}
