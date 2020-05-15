package com.TradingWebsite.Model;

import lombok.Data;

@Data
/**
 * 管理员表
 */
public class Manager{
    private long aid;
    private String name;
    private String password;
    private String email;

}
