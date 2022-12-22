package com.fly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自定义用户、密码、角色(权限)数据源
     * @return InMemoryUserDetailsManager 基于内存用户详情管理器
     */
    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        /**
         * admin拥有ADMIN、TEST角色,根据URL权限配置登录认证成功后登录用户只要有
         * ADMIN或TEST角色,允许访问/admin和/test,访问其他资源时会出现403(拒绝授权访问)。
         */
        manager.createUser(User.withUsername("admin").password("{noop}123")
                .roles("ADMIN","USER").build());
        /**
         * 由于user只有USER角色,根据URL权限配置登录认证成功后只能访问/user,
         * 访问其他资源时会出现403(拒绝授权访问)。
         */
        manager.createUser(User.withUsername("user").password("{noop}123")
                .roles("USER").build());
        /**
         * 由于userList只有ADMIN权限,根据URL权限配置登录认证成功后只能访问/userList,
         * 访问其他资源时会出现403(拒绝授权访问)。
         */
        manager.createUser(User.withUsername("userList").password("{noop}123")
                .authorities("USER_LIST").build());
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 匹配Http GET请求的 /admin和/test URL
                .mvcMatchers(HttpMethod.GET,"/admin","/test")
                .hasAnyRole("ADMIN","TEST")
                .mvcMatchers("/user")
                .hasRole("USER")
                .mvcMatchers("/userList")
                .hasAuthority("USER_LIST")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .csrf()
                .disable();
    }
}
