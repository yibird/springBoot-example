package com.fly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration // 标识当前类为配置类
@EnableWebSecurity // 启用Spring Security
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * 自定义内存数据源方式2:向Spring IOC注入UserDetailsService自定义数据源
     *
     * @return 用户身份信息
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}user")
                .roles("ADMIN", "USER")
                .build();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(user);
        return manager;
    }

    /**
     * 自定义内存数据源方式1:基于AuthenticationManager全局配置自定义数据源
     *
     * @param build the {@link AuthenticationManagerBuilder} to use
     * @throws Exception 认证出现的异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder build) throws Exception {
        UserDetails user = User.builder()
                // 登录用户名
                .username("user")
                // 登录用户密码,{noop}表示明文加密策略
                .password("{noop}123")
                // 设置登录用户角色
                .roles("ADMIN", "USER")
                .build();
        // 基于内存存储用户信息
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(user);
        /**
         * 根据传入的自定义UserDetailsService添加身份验证。然后,
         * 它返回一个DaoAuthenticationConfigurer,以允许自定义身份验证。
         */
        build.userDetailsService(manager);
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .csrf()
                .disable();
    }
}
