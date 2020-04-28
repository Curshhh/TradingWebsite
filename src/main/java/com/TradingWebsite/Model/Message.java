package com.TradingWebsite.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

//留言板
@Data
public class Message {
    private long mid;//留言板id
    private String content;//留言
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private String modify;//留言时间
    private long cid;//留言的商品
    private long uid;//用户id

    private String name;//用户昵称
    private String email;//用户邮件
}
