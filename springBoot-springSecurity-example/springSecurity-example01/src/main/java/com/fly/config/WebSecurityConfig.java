package com.fly.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration // 标识当前类为配置类
@EnableWebSecurity // 启用Spring Security
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 对于所有请求都需要认证授权,匹配/index请求则放行请求,对于任何请求都必须在
         * 认证成功后才能访问,认证方式采用form表单认证。
         */
        http.authorizeRequests()
                .mvcMatchers("/index","/login.html")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                // 指定form登录页面,指定登录页面必须指定loginProcessingUrl
                .loginPage("/login.html")
                // 指定登录名参数名称,默认username,该参数名需要与表单页面中的用户名输入框name属性一致,否则登录失败
                .usernameParameter("username")
                // 指定登录密码参数名称,默认password,该参数名需要与表单页面中的密码输入框name属性一致,否则登录失败
                .passwordParameter("password")
                // 指定处理登录请求url,SpringSecurity默认使用/login处理登录请求
                .loginProcessingUrl("/doLogin")
                // 认证成功重定向(redirect)的URL,alwaysUse参数表示是否跳转到认证前的URL,false表示跳转到/index
                // .defaultSuccessUrl("/index",false)
                // 认证成forward(转发)的URL
                // .successForwardUrl("/index")
                // 指定登录成功处理
                .successHandler(new CustomAuthenticationSuccessHandler())
                // 指定认证失败转发URL
                // .failureForwardUrl("/failure")
                .failureHandler(new CustomAuthenticationFailureHandler())
                .and()
                // 注销认证
                .logout()
                // 退出登录后跳转的URL
                .logoutUrl("/logout")
                // 配置SecurityContextLogoutHandler以在注销时使HttpSession无效
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                // 注销后重定向到的URL,默认为"/login?logout"
                // .logoutSuccessUrl("/login.html?logout")
                .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                .and()
                // 禁用csrf(),不禁用csrf()会导致登录请求302
                .csrf().disable();
    }
}
