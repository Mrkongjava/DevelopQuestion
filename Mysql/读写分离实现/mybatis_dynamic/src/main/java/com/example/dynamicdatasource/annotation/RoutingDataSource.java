package com.example.dynamicdatasource.annotation;

import com.example.dynamicdatasource.config.Datasources;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据源切换注解
 */
//是注解存在的范围，RUNTIME代表的是注解会在class字节码文件中存在，在运行时可以通过反射获取到，具体看：RetentionPolicy
@Retention(RetentionPolicy.RUNTIME)
//作用目标为方法，只对被修饰的方法有效
@Target(ElementType.METHOD)
public @interface RoutingDataSource {
    //设置默认值为Datasources.MASTER_DB
    String value() default Datasources.MASTER_DB;
}