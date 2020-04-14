package com.TradingWebsite.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.sql.DataSourceDefinition;
import java.beans.PropertyVetoException;

/*
* 配置DataSource到ioc容器
*/
@Configuration
/*属性注入*/
@EnableConfigurationProperties(JdbcProperties.class)
/*启用配置JdbcProperties配置属性*/
@MapperScan("com.TradingWebsite.Dao")
/*@MapperScan 配置一个或多个包路径，自动的扫描这些包路径下的类，自动的为它们生成代理类。*/
public class DataSourceConfig {
    /*@Value("${jdbc.driver}")
    private String jdbcDriver;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUsername;
    @Value("${jdbc.password}")
    private String jdbcPassword;
*/
    @Bean(name="dataSource")
    public ComboPooledDataSource createDataSource(JdbcProperties prop) throws PropertyVetoException {
//生成datasource实例
        ComboPooledDataSource dataSource =new ComboPooledDataSource();

//驱动
        dataSource.setDriverClass(prop.getDriver());
//数据库连接URL
        dataSource.setJdbcUrl(prop.getUrl());
//设置用户名
        dataSource.setUser(prop.getUsername());
//设置用户密码
        dataSource.setPassword(prop.getPassword());
        //配置c3pO连接池的私有属性
//连接池最大线程数
        dataSource.setMaxPoolSize(30);
//连接池最小线程数
        dataSource.setMinPoolSize(10);
//关闭连接后不自动commit
        dataSource.setAutoCommitOnClose(false);
//连接超时时间
        dataSource.setCheckoutTimeout(10000);
//连接失败重试次数
        dataSource.setAcquireRetryAttempts(2);
        return dataSource;
    }
}
