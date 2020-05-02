package com.fly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.fly.dao")
@SpringBootApplication
/**
 * 自动扫描com.fly.dao包的组件
 */
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
