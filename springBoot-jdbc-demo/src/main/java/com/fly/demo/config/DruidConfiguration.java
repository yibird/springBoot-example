package com.fly.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DruidConfiguration {

    /**
     * 注入druid数据源
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix ="spring.datasource")
    public DataSource dataSource(){
        return new DruidDataSource();
    }

    /**
     * 注册Servlet Bean
     * @return
     */
    @Bean
    public ServletRegistrationBean druidStatViewServlet(){
        ServletRegistrationBean registrationBean=new
                ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        //添加白名单,下面的设置表示允许127.0.0.1访问
        registrationBean.addInitParameter("allow","127.0.0.1");
        //设置黑名单(同时存在时,deny优先于allow),如果满足deny，就提示：sorry，you are not permitted to view this page
        registrationBean.addInitParameter("deny","192.168.1.73");

        //登陆用户名和密码
        registrationBean.addInitParameter("loginUsername", "admin");
        registrationBean.addInitParameter("loginPassword", "123456");
        registrationBean.addInitParameter("resetEnable", "false");
        return registrationBean;
    }

    /**
     * 注册过滤器bean
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean(new WebStatFilter());
        //设置过滤规则,下面配置表示过滤所有路径
        filterRegistrationBean.addUrlPatterns("/*");
        //添加需要忽略的格式信息
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif," +
                "*.jpg,*.png, *.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }


}
