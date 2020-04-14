package com.TradingWebsite.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "jdbc")
/*
*将本类中的所有属性和配置文件中相关的配置进行绑定
*prefix前缀申明*/
@Data
public class JdbcProperties {
    String url;
    String driver;
    String username;
    String password;
}
