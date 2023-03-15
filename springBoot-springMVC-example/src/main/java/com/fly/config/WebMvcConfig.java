package com.fly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Executor;

/**
 * Web配置类,启用Servlet异步请求
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    /**
     * 配置异步请求处理选项。configureAsyncSupport()支持配置默认异步请求超时时间、
     * 异步任务执行器及注册CallableInterceptors和DeferredResultInterceptors接口的实现。
     *
     * @param configurer 异步支持配置器
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        /**
         * 指定异步请求处理超时的时间(以毫秒为单位),在Servlet 3中,超时请求在主处理线程退出后开始,
         * 在再次调度请求以进一步处理并发生成的结果时结束。如果未设置此值,则使用基础实现的默认超时。
         * 除了使用setDefaultTimeout()方法设置超时时间,也可以在DeferredResult、
         * ResponseBodyEmitter、SseEmitter、Callable、WebAsyncTask对象设置请求超时时间,
         * 这些对象都提供了设置请求超时时间方法。
         */
        configurer.setDefaultTimeout(120 * 1000L); // 超时时间12s
        /**
         * 设置任务执行器。任务执行器有两个作用:
         * 1.处理Callable控制器方法返回值。
         * 2.当通过反应式(例如Reactor、RxJava)控制器方法返回值流到响应时,执行阻塞写入。
         * 默认情况下,仅使用SimpleAsyncTaskExecutor。但是,在使用上述两个用例时,
         * 建议配置一个由线程池(如ThreadPoolTaskExecutor)支持的执行器。
         */
        configurer.setTaskExecutor(asyncTaskExecutor());
        /**
         * 注册Callable拦截器。在控制器返回Callable时开始的并发请求执行周围配置
         * 带有回调的生命周期拦截器。
         */
        configurer.registerCallableInterceptors();
        /**
         * 注册DeferredResult拦截器。在控制器返回DeferredResult时开始的并发请求
         * 执行周围配置具有回调的生命周期拦截器。
         */
        configurer.registerDeferredResultInterceptors();
    }

    /**
     * 向IOC容器注入ThreadPoolExecutor。ThreadPoolExecutor支持以JavaBean的
     * 方式配置ThreadPoolExecutor。
     *
     * @return
     */
    @Bean
    public AsyncTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置ExecutorService
        executor.initialize();
        // ThreadPoolExecutor线程池核心数,默认1
        executor.setCorePoolSize(5);
        // ThreadPoolExecutor线程池最大线程池(默认无限制)
        executor.setMaxPoolSize(10);
        /**
         * ThreadPoolExecutor线程池队列容量(默认无限制),队列容量为0时相当于
         * java.util.concurrent.Executors.newCachedThreadPool(),
         * 可以立即将池中的线程扩展到可能非常高的数量
         */
        executor.setQueueCapacity(100);
        return executor;
    }
}
