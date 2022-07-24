package com.fuyusakaiori.csms.config.dao;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class SqlSessionFactoryConfiguration {

    @Value("${mybatis.config-location}")
    private String mybatisConfigPath;
    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;
    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;
    @Resource
    private DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean getSqlSessionFactory() throws IOException {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        // 1. 设置数据源
        sqlSessionFactory.setDataSource(this.dataSource);
        // 2. 设置配置文件
        sqlSessionFactory.setConfigLocation(new ClassPathResource(mybatisConfigPath));
        // 3. 设置别名
        sqlSessionFactory.setTypeAliasesPackage(typeAliasesPackage);
        // 4. 设置扫描路径
        sqlSessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + mapperLocations));
        return sqlSessionFactory;
    }

}
