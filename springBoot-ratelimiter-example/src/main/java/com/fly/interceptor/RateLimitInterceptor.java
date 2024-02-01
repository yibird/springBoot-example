package com.fly.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description 限流拦截器
 * @Author zchengfeng
 * @Date 2024/1/26 13:41:28
 */
public class RateLimitInterceptor implements HandlerInterceptor {

    /**
     * 处理目标handler之前执行,返回一个布尔值,如果返回false,则不执行目标handler
     * @param request 当前HTTP request对象
     * @param response 当前HTTP response对象
     * @param handler 选择要执行的handler
     * @return 布尔值
     * @throws Exception Exception
     */
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    /**
     * 处理目标handler之后执行
     * @param request 当前HTTP request对象
     * @param response 当前HTTP response对象
     * @param handler the handler (or {@link HandlerMethod}) that started asynchronous
     * execution, for type and/or instance examination
     * @param modelAndView handler返回的modelAndView,或者null
     * @throws Exception Exception
     */
    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     *
     * @param request 当前HTTP request对象
     * @param response 当前HTTP response对象
     * @param handler the handler (or {@link HandlerMethod}) that started asynchronous
     * execution, for type and/or instance examination
     * @param ex handler执行时引发的任何异常,不包括已通过异常解析程序处理的异常
     * @throws Exception Exception
     */
    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
