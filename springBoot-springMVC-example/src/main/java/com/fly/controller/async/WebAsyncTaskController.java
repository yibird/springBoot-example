package com.fly.controller.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.Callable;

/**
 * Web异步任务控制器。WebAsyncTask表示Callable、超时值和任务执行器的持有者。
 * WebAsyncTask提供了多个构造函数,其中WebAsyncTask(@Nullable Long timeout, AsyncTaskExecutor executor,
 * Callable<V> callable)最为重要:
 * - timeout:请求超时时间。
 * - executor:异步任务执行器,用于处理异步任务请求。
 * - callable:WebAsyncTask处理函数。
 */
@RestController
public class WebAsyncTaskController {

    @Autowired
    AsyncTaskExecutor asyncTaskExecutor;

    @GetMapping("/webAsyncTask")
    public WebAsyncTask<String> webAsyncTask() {
        Callable<String> callable = () -> "hello webAsyncTask";
        WebAsyncTask<String> task = new WebAsyncTask<>(1000 * 12L, asyncTaskExecutor, callable);
        // 注册请求完成时回调方法
        task.onCompletion(() -> {
            System.out.println("request complete...");
        });
        // 注册请求错误时回调方法
        task.onError(() -> {
            System.out.println("request error...");
            return "error";
        });
        // 注册请求超时回调方法
        task.onTimeout(() -> {
            System.out.println("request timeout...");
            return "timeout";
        });
        return task;
    }
}
