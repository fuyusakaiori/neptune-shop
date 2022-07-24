package com.fuyusakaiori.csms.config.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * <h3>{@code EnableTransactionManagement} 开启事务支持</h3>
 */
@Configuration
@EnableTransactionManagement
public class TransactionManagerConfiguration implements TransactionManagementConfigurer {

    @Resource
    private DataSource dataSource;

    @Override
    public TransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
