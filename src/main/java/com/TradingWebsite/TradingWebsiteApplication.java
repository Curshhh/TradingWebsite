package com.TradingWebsite;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
//@MapperScan({"com.TradingWebsite.Dao"})
/*1.申明配置类
* 2.开启自动配置
* 3.扫描包*/
public class TradingWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradingWebsiteApplication.class, args);
    }

}
