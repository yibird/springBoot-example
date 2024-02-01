package com.fly.ds.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author zchengfeng
 * @Date 2024/1/25 22:47:51
 */

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.dynamic")
public class DataSourceConfig {
    private String primary;
    private boolean strict;
    private Map<String, DataSourceProperties> dataSources = new HashMap<>();
}
