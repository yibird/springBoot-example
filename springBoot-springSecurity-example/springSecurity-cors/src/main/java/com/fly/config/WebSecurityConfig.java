package com.fly.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .cors() // 开启cors跨域
                .configurationSource(configurationSource()) // 配置CorsConfigurationSource
                .and()
                .csrf().disable(); // 禁用csrf
    }
    public CorsConfigurationSource configurationSource(){
        // 初始化Cors配置类
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许所有的请求域名访问的跨域资源,*表示任意源
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        // 允许跨域请求方法
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "HEAD", "PUT", "DELETE", "OPTIONS"));
        // 是否允许请求携带证书
        corsConfiguration.setAllowCredentials(true);
        // 设置再次发起预检请求的过期时间
        corsConfiguration.setMaxAge(3600L);
        // 使用URL路径模式为请求选择CorsConfiguration。
        UrlBasedCorsConfigurationSource source =new UrlBasedCorsConfigurationSource();
        // 添加corsConfiguration和URL跨域映射,/**表示允许所有请求跨域
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }
}
