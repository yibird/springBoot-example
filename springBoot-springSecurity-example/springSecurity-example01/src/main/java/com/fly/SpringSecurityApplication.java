package com.fly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class);
    }

    @GetMapping("/hello")
    public String hello() {
        /**
         * 用户认证后用户信息。通过SecurityContextHolder.getContext()获取
         * SecurityContext对象,通过SecurityContext.getAuthentication()
         * 获取Authentication对象,Authentication对象包含了认证后的用户信息
         */
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        System.out.println("authentication:"+authentication);
        /**
         * Authentication对象提供如下方法用于获取验证用户信息:
         * - getPrincipal():用于获取正在验证的主体的身份。对于带有用户名和密码的身份验证请求,
         * 这将是用户名。
         * - getAuthorities():由AuthenticationManager设置,以表示已授予主体的权限。
         * - getCredentials():获取证明主体正确的凭据。这通常是一个密码,但可以是与AuthenticationManager
         * 相关的任何内容,调用方应填充凭据。
         * - getDetails():存储有关身份验证请求的其他详细信息。这些可能是IP地址、证书序列号等。
         */
        return "hello";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/failure")
    public String failure() {
        return "failure";
    }
}