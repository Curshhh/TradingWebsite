package com.TradingWebsite.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

//留言板
@Data
public class Message {
    private long mid;//留言板id
    private String content;//留言
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private String modify;//留言时间
    private long cid;//留言的商品
    private long uid;//用户id
}
