package com.example.dynamicdatasource.config;

import lombok.extern.slf4j.Slf4j;

/**
 * @author pangenshan
 * @version 1.0
 * @date 2019/1/7 16:54
 */
@Slf4j
public class DatasourceContextHolder {
    //默认数据源
    public static final String DEFAULT_DATASOURCE = Datasources.MASTER_DB;

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    //设置数据源名字
    public static void setDB(String dbType) {
        log.debug("切换到{}数据源", dbType);
        contextHolder.set(dbType);
    }

    /**
     * 获取数据源名
     */
    public static String getDB() {
        return contextHolder.get();
    }

    /**
     * 清除数据源名
     */
    public static void clearDB() {
        contextHolder.remove();
    }
}