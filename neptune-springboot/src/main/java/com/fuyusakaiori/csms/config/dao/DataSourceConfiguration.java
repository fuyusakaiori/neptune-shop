package com.fuyusakaiori.csms.config.dao;

import com.fuyusakaiori.csms.util.encrypt.DESUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

@Configuration
@MapperScan("com.fuyusakaiori.csms.dao")
public class DataSourceConfiguration {

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private final static int maxPoolSize = 100;

    private final static int minPoolSize = 50;

    private final static boolean autoCommitOnClose = false;

    private final static int acquireRetryAttempts = 5;

    private final static int checkOuTimeOut = 10000;

    @Bean(name = "dataSource")
    public ComboPooledDataSource getDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        // 1. 配置数据源基本信息
        dataSource.setDriverClass(driver);
        dataSource.setJdbcUrl(url);
        dataSource.setUser(DESUtil.getDecryption(username));
        dataSource.setPassword(DESUtil.getDecryption(password));
        // 2. 配置数据库连接池信息
        dataSource.setMaxPoolSize(maxPoolSize);
        dataSource.setMinPoolSize(minPoolSize);
        dataSource.setAutoCommitOnClose(autoCommitOnClose);
        dataSource.setAcquireRetryAttempts(acquireRetryAttempts);
        dataSource.setCheckoutTimeout(checkOuTimeOut);
        // 3. 返回数据源对象
        return dataSource;
    }

}
