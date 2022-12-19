package com.fly.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义认证成功处理
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "登录成功");
        result.put("authentication", authentication);
        // 设置响应内容类型
        response.setContentType("application/json;charset=UTF-8");
        /**
         * 用于将任何Java值序列化为字符串,在功能上等同于使用StringWriter
         * 调用writeValue(Writer，Object)并构造String,但效率更高。
         */
        String str = new ObjectMapper().writeValueAsString(result);
        // 获取写入器并打印str
        response.getWriter().println(str);
    }
}
