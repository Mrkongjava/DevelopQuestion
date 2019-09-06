package com.example.dynamicdatasource.config;

import com.google.common.collect.Maps;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Map;

/**
 * mybatis 配置
 */
@Configuration
@MapperScan(basePackages = {"com.example.dynamicdatasource.dao"})
public class MybatisConfig {
    @Resource
    @Qualifier(Datasources.MASTER_DB)
    private DataSource masterDB;

    @Resource
    @Qualifier(Datasources.SLAVE_DB)
    private DataSource slaveDB;

    /**
     * 动态数据源
     * @return DataSource
     */
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //配置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(masterDB);

        //多数据源配置
        Map<Object, Object> dsMap = Maps.newHashMap();
        dsMap.put(Datasources.MASTER_DB, masterDB);
        dsMap.put(Datasources.SLAVE_DB, slaveDB);
        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;
    }

    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource 作为数据源则不能实现切换
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        return sqlSessionFactoryBean;
    }
}