package com.TradingWebsite.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

@Data
/*使用lombok插件*/
public class User implements Serializable {

    private long id;//用户id
    private String name;//用户昵称
    private String password;//用户密码
    private String sex;//用户性别
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private String birthday;//生日
    private String phone;//手机号
    private String email;//邮箱
    private String address;//地址
    private int status;//激活状态0为激活，1为激活
    private String code;//激活码
    private int power;//1只能购买不能发布，2可以购买和发布
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private String modify;//注册时间

}
