package com.fly.filter;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration // 标识当前类Spring配置类
/**
 * 用于声明Servlet Filter此注释将在部署期间由容器处理,在其中找到它的Filter
 * 类将根据配置创建并应用于URL模式javax.servlet。该注解支持如下参数:
 * - value:value属性是urlPatterns属性的别名。
 * - urlPatterns:应用此Filter的URL模式数组。
 * - filterName:过滤器的名称,默认为@WebFilter注解所表示类的全限定类名。
 * - displayName:过滤器显示的名称。
 * - description:过滤器描述。
 */
@WebFilter(filterName = "CorsFilter")
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter初始化...");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        // 设置跨域源
        response.setHeader("Access-Control-Allow-Origin","*");
        // 设置请求是否允许携带证书
        response.setHeader("Access-Control-Allow-Credentials","true");
        // 设置请求跨域方法
        response.setHeader("Access-Control-Allow-Methods","POST,GET,DELETE,PUT");
        // 置再次发起预检请求的过期时间
        response.setHeader("Access-Control-Max-Age","3600");
        // 允许跨域请求header信息
        response.setHeader("Access-Control-Allow-Headers","Origin,X-Requested-With,Content-Type,Accept");
    }

    @Override
    public void destroy() {
        System.out.println("Filter销毁...");
        Filter.super.destroy();
    }
}
