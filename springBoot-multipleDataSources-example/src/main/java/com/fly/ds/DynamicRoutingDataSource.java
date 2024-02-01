package com.fly.ds;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Description 动态数据源类
 * @Author zchengfeng
 * @Date 2024/1/25 17:23:25
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource implements InitializingBean {

    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println("Asdasdasd");
        return null;
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("Asdasd哈哈哈哈哈哈");
    }
}
