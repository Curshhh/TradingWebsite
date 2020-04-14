package com.TradingWebsite.Uitls;

import java.util.UUID;

/* 1.使用UUID来对图片名称进行重新生成。
 * 2.生成邮件验证码
 * */
public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
