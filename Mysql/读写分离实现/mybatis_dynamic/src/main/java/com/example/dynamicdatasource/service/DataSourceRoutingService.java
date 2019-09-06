package com.example.dynamicdatasource.service;

import com.example.dynamicdatasource.annotation.RoutingDataSource;
import com.example.dynamicdatasource.config.Datasources;
import com.example.dynamicdatasource.dao.SysUserMapper;
import com.example.dynamicdatasource.domain.SysUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 模拟数据源切换
 */
@Service
public class DataSourceRoutingService {
    @Resource
    private SysUserMapper sysUserMapper;

    @RoutingDataSource(value = Datasources.MASTER_DB)
    public SysUser test1(long id) {
        return sysUserMapper.findById(id);
    }

    @RoutingDataSource(value = Datasources.SLAVE_DB)
    public SysUser test2(long id) {
        return sysUserMapper.findById(id);
    }
}