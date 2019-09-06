package com.example.dynamicdatasource.aspect;

import com.example.dynamicdatasource.annotation.RoutingDataSource;
import com.example.dynamicdatasource.config.DatasourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * aop 拦截注解
 */
@Slf4j
@Aspect
@Component
public class DynamicDataSourceAspect {
    @Before("@annotation(com.example.dynamicdatasource.annotation.RoutingDataSource)")
    public void beforeSwitchDS(JoinPoint joinPoint) {

        //得到访问的方法对象
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        //从ThreadLocal中取出默认的数据源名称
        String dataSource = DatasourceContextHolder.DEFAULT_DATASOURCE;
        //判断是否存在注解@RoutingDataSource
        if (method.isAnnotationPresent(RoutingDataSource.class)) {
            RoutingDataSource routingDataSource = method.getDeclaredAnnotation(RoutingDataSource.class);
            //取出注解中的数据源名称，并赋值
            dataSource = routingDataSource.value();
        }
        //设置数据源名称
        DatasourceContextHolder.setDB(dataSource);
    }

    /**
     * 方法使用完后，要清空DatasourceContextHolder
     */
    @After("@annotation(com.example.dynamicdatasource.annotation.RoutingDataSource)")
    public void afterSwitchDS() {
        DatasourceContextHolder.clearDB();
    }
}