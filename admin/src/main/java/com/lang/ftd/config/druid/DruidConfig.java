package com.lang.ftd.config.druid;

import com.alibaba.druid.pool.DruidDataSource;  

import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
import org.springframework.beans.factory.annotation.Value;  
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.context.annotation.Primary;  
  

import javax.sql.DataSource;  

import java.sql.SQLException;  

import lombok.extern.slf4j.Slf4j;
  
@Configuration  
@Slf4j
public class DruidConfig {  
  
    @Value("${spring.datasource.driver-class-name}")  
    private String driverClassName;  
    @Value("${spring.datasource.url}")  
    private String url;  
    @Value("${spring.datasource.username}")  
    private String username;  
    @Value("${spring.datasource.password}")  
    private String password;  
    @Value("${spring.datasource.initial-size}")  
    private int initialSize;  
    @Value("${spring.datasource.min-idle}")  
    private int minIdle;  
    @Value("${spring.datasource.max-active}")  
    private int maxActive;  
    @Value("${spring.datasource.max-wait}")  
    private int maxWait;  
    @Value("${spring.datasource.validationQuery}")  
    private String validationQuery;  
    @Value("${spring.datasource.testOnBorrow}")  
    private boolean testOnBorrow;  
    @Value("${spring.datasource.testOnReturn}")  
    private boolean testOnReturn;  
    @Value("${spring.datasource.testWhileIdle}")  
    private boolean testWhileIdle;  
    @Value("${spring.datasource.time-between-eviction-runsMillis}")  
    private int timeBetweenEvictionRunsMillis;  
    @Value("${spring.datasource.min-evictableIdleTimeMillis}")  
    private int minEvictableIdleTimeMillis;  
    @Value("${spring.datasource.removeAbandoned}")  
    private boolean removeAbandoned;  
    @Value("${spring.datasource.removeAbandonedTimeout}")  
    private int removeAbandonedTimeout;  
    @Value("${spring.datasource.logAbandoned}")  
    private boolean logAbandoned;  
    @Value("${spring.datasource.filters}")  
    private String filters;  
    @Value("${spring.datasource.poolPreparedStatements}")  
    private boolean poolPreparedStatements;  
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")  
    private int maxPoolPreparedStatementPerConnectionSize;  
  
    @Bean(destroyMethod = "close", initMethod = "init")  
    @Primary  
    public DataSource dataSource() {  
        DruidDataSource datasource = new DruidDataSource();  
  
        datasource.setDriverClassName(driverClassName);  
        datasource.setUrl(url);  
        datasource.setUsername(username);  
        datasource.setPassword(password);  
        //其它配置  
        datasource.setInitialSize(initialSize);  
        datasource.setMinIdle(minIdle);  
        datasource.setMaxActive(maxActive);  
        datasource.setMaxWait(maxWait);  
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);  
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);  
        datasource.setValidationQuery(validationQuery);  
        datasource.setTestWhileIdle(testWhileIdle);  
        datasource.setTestOnBorrow(testOnBorrow);  
        datasource.setTestOnReturn(testOnReturn);  
        datasource.setPoolPreparedStatements(poolPreparedStatements);  
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);  
        try {  
            datasource.setFilters(filters);  
        } catch (SQLException e) {  
            log.error("druid configuration initialization filter", e);  
        }  
        return datasource;  
    }  
  
} 