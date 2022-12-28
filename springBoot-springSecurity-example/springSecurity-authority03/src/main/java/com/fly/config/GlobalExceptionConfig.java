package com.fly.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理配置类
 */
@ControllerAdvice
public class GlobalExceptionConfig {

    /**
     * 处理访问被拒绝异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Map<String,Object> exception(HttpServletRequest req, Exception e){
        System.out.println("e:"+e);
        Map<String,Object> map = new HashMap<>();
        map.put("code", HttpStatus.FORBIDDEN.value());
        map.put("data",null);
        map.put("msg","无权限访问");
        return map;
    }
}
