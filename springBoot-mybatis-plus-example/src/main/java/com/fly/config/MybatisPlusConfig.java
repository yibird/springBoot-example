package com.fly.config;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fly.interceptor.DesensitizationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@MapperScan("com.fly.mapper")
public class MybatisPlusConfig {

    /**
     * 注入脱敏拦截器,@Order()用于设置Bean的注入顺序
     * @return
     */
    @Bean
    @Order(-1)
    public DesensitizationInterceptor desensitizationInterceptor(){
        return new DesensitizationInterceptor();
    }

    /**
     * MybatisPlus分页插件
     * @return MybatisPlusInterceptor(MybatisPlus拦截器)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加自动分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        /**
         * 添加SQL性能规范插件,当编写了不规范的SQL语句时就会被拦截,拦截SQL类型的场景:
         * - 必须使用到索引,包含left join连接字段,符合索引最左原则。
         * - SQL尽量单表执行,有查询left join的语句,必须在注释里面允许该SQL运行,否则会被拦截。
         * - where条件为空。
         * - where条件使用了 !=。
         * - where条件使用了 not 关键字。
         * - where条件使用了 or 关键字。
         * - where条件使用了 使用子查询。
         * 注意:可使用注解 @InterceptorIgnore(illegalSql = “1”)忽略此插件
         */
//        interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
        return interceptor;
    }


    /**
     * 自定义MybatisPlus配置
     * @return
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseGeneratedKeys(false);
    }
}
