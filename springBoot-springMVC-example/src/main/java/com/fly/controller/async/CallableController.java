package com.fly.controller.async;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.Callable;

/**
 * Callable异步请求控制器。控制器方法可以使用java.util.concurrent.Callable包装
 * 任何受支持的返回值。
 */
@RestController
public class CallableController {
    @GetMapping("/callable")
    public Callable<String> callable() {
        return ()->"hello callable";
    }
}
