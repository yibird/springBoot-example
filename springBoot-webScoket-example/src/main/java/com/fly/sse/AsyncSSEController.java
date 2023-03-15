package com.fly.sse;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 异步任务方式实现SSE推送消息
 */
@RestController
@RequestMapping("/sse")
@CrossOrigin(origins = "*")
public class AsyncSSEController {
    // 创建容器用于保存连接
    private Map<String, PrintWriter> responseMap = new ConcurrentHashMap<>();

    /**
     * 发送消息
     *
     * @param id      唯一标识
     * @param message 发送消息
     * @param over    是否关闭连接
     */
    private void send(String id, String message, boolean over) {
        // 根据id从map中获取打印写入器
        PrintWriter writer = responseMap.get(id);
        if (writer == null) {
            return;
        }
        System.out.println("message：" + message);
        // 发送消息打印并刷新,发送消息时需要注意SSE的数据格式
        writer.println("data:  " + message + "\n\n");
        writer.flush();
        // 判断是否需要关闭连接
        if (over) {
            responseMap.remove(id);
        }
    }

    /**
     * @param id       唯一标识
     * @param response http响应对象
     * @return 异步任务实例
     */
    @GetMapping("/asyncTask/subscribe")
    @ResponseBody
    public WebAsyncTask<Void> subscribe(String id, HttpServletResponse response) {
        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Callable<Void> callable = () -> {
            response.setHeader("Content-Type", "text/event-stream;charset=UTF-8");
            responseMap.put(id, response.getWriter());
            // 通过调度线程池每隔1s使用SseEmitter向客户端推送当前时间
            pool.scheduleAtFixedRate(() -> {
                send(id, format.format(new Date()), false);
            }, 0, 1000, TimeUnit.MILLISECONDS);
            // 通过循环实现长连接,保证连接不被中断
            while (true) {
                if (!responseMap.containsKey(id)) {
                    break;
                }
            }
            return null;
        };
        /**
         * 创建异步任务并指定超时时间和执行器
         */
        WebAsyncTask<Void> webAsyncTask = new WebAsyncTask<>(180000L, callable);
        // 声明完成回调,无论请求超时或异常都会执行该回调
        webAsyncTask.onCompletion(() -> {
            System.out.println("sse completion");
        });
        // 声明请求超时回调
        webAsyncTask.onTimeout(() -> {
            responseMap.remove(id);
            System.out.println("sse超时");
            return null;
        });
        // 声明请求异常回调
        webAsyncTask.onError(() -> {
            System.out.println("sse异常");
            return null;
        });
        return webAsyncTask;
    }
}
