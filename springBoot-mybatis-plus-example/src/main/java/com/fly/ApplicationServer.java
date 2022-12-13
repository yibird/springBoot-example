package com.fly;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/**
 * 指定扫描Mapper接口的基础包路径,并将这些Mapper接口注入到Spring IOC容器中,
 * 设置该注解后等同于指定包下的每个Mapper接口添加了@Mapper注解,
 * 若不设置@MapperScan则每个Mapper接口都需要使用@Mapper
 * 注解将Mapper接口注入到Spring容器中
 */
@MapperScan(basePackages = "com.fly.mapper")
public class ApplicationServer {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationServer.class);
    }
}