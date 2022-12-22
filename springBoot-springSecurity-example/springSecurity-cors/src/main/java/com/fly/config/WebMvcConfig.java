package com.fly.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * 自定义SpringMVC配置类。
 * WebMvcConfigurer提供了扩展SpringMVC配置的功能,用于定义回调方法。
 * 通过@EnableWebMvc启用的Spring MVC的基于Java的配置。
 * 带@EnableWebMvc注解的配置类可以实现此接口,以便回调并提供定制默认配置的机会。
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 添加跨域配置
     * @param registry 帮助注册基于URL模式的全局CorsConfiguration映射。
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        /**
         * 添加URL跨域映射,/**表示允许所有请求跨域,允许所有请求跨域可能存在安全性问题,
         * 一般推荐指定允许跨域URL,例如: 127.0.0.1,www.baidu.com
         */
        registry.addMapping("/**")
                // 允许所有的请求域名访问的跨域资源,*表示任意源
                .allowedOrigins("*")
                // 允许跨域请求方法
                .allowedMethods("GET", "POST", "HEAD", "PUT", "DELETE", "OPTIONS")
                // 是否允许请求携带证书
                .allowCredentials(true)
                // 设置再次发起预检请求的过期时间
                .maxAge(3600)
                /**
                 * 允许请求header的信息,*表示允许所有的请求header访问。
                 * 可以自定义设置任意请求头信息。
                 */
                .allowedHeaders("*");
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(){
        // 过滤器注册Bean,用于将Filter注册为Bean
        FilterRegistrationBean<CorsFilter> registrationBean =new
                FilterRegistrationBean<>();
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
        // 注册Cors过滤器
        registrationBean.setFilter(new CorsFilter(source));
        // 设置注册bean的顺序,注册顺序根据order从小到大注册
        registrationBean.setOrder(-1);
        return registrationBean;
    }
}
