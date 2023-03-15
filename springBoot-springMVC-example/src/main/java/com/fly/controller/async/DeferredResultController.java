package com.fly.controller.async;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * DeferredResult异步请求控制器
 */
@RestController
public class DeferredResultController {

    /**
     * DeferredResult用于处理异步请求,是Callable的替代方案。当代表应用程序
     * 并发执行Callable时,使用DeferredResult,应用程序可以从其选择的线程生成结果。
     *
     * DeferredResult接收一个泛型参数来表示延迟结果的返回值类型,DeferredResult提供如下方法:
     * - setResult(T result):设置DeferredResult的值并处理它。
     *
     * - setResultHandler(DeferredResultHandler resultHandler):提供用于处理结果值的处理程序。
     * DeferredResultHandler是DeferredResult类中内部的接口,实现DeferredResultHandler接口
     * 需要重写handleResult(Object result)方法来处理结果值,其中result表示处理结果值。
     *
     * - setErrorResult(Object result):为DeferredResult设置一个错误值并对其进行处理。
     * 该值可以是Exception或Throwable,在这种情况下,将像处理程序引发异常一样处理该值。
     *
     * - isSetOrExpired():如果此DeferredResult由于先前设置或基础请求已过期而不再可用,则返回true。
     *
     * - getResult():返回结果，如果未设置结果，则返回null。由于结果也可以为空,
     * 建议在调用此方法之前先使用hasResult()检查是否有结果。
     *
     * - hasResult():如果已设置DeferredResult,则返回true。
     *
     * - onCompletion(Runnable callback):注册异步请求完成时要调用的代码。当异步请求由于任何原因
     * (包括超时和网络错误)完成时,从容器线程调用此方法。这对于检测DeferredResult实例不再可用非常有用。
     *
     * - onError(Consumer<Throwable> callback):注册在异步请求期间发生错误时要调用的代码。当在填充
     * DeferredResult之前处理异步请求时发生错误时，从容器线程调用此方法。它可以调用setResult
     * 或setErrorResult来恢复处理。
     *
     * - onTimeout(Runnable callback):注册异步请求超时时要调用的代码。当异步请求在填充DeferredResult之前超时时,
     * 从容器线程调用此方法。它可以调用setResult或setErrorResult来恢复处理。
     *
     * @return DeferredResult
     */
    @GetMapping("/deferredResult")
    public DeferredResult<String> deferredResult(){
        System.out.println("deferredResult:");
        DeferredResult<String> deferredResult = new DeferredResult();
        // 设置响应结果
        deferredResult.setResult("hello DeferredResult");
        // 判断是否存在处理结果,如果存在则获取处理结果
        if(deferredResult.hasResult()){
            System.out.println("result:"+deferredResult.getResult());
        }
        // 设置响应结果处理器
        deferredResult.setResultHandler(result -> {
            System.out.println("result:"+result);
        });
        // 注册请求完成时回调方法
        deferredResult.onCompletion(()->{
            System.out.println("request complete...");
        });
        // 注册请求错误时回调方法
        deferredResult.onError((t)->{
            System.out.println("request error...,cause:"+t.getCause());
        });
        // 注册请求超时回调方法
        deferredResult.onTimeout(()->{
            System.out.println("request timeout...");
        });
        return deferredResult;
    }
}
