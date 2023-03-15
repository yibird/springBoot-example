package com.fly.sse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 使用sse进行服务端消息推送。Spring提供了SseEmitter类用于发送服务器消息。
 *
 * SSE规范要求服务端请求头遵循如下规则:
 * Content-Type: text/event-stream;charset=UTF-8
 * Cache-Control: no-cache
 * Connection: keep-alive
 * <p>
 * SSE数据格式为 field:value\n\n ,其中value表示发送的消息体,\n\n表示消息结尾,field五种可选值:
 * - 空:即以:开头表示注释,可以理解为服务端向客户端发送的心跳,确保连接不中断。
 * - data:数据。
 * - event:事件,默认值。
 * - id:数据标识符用 id 字段表示,相当于每一条数据的编号。
 * - retry:重连时间。
 * <p>
 * SseEmitter对象提供如下方法:
 * - send():发送数据,如果传入的是一个非SseEventBuilder对象,那么传递参数会被封装到 data 中。
 * - complete():表示执行完毕,会断开连接。
 * - onTimeout():指定sse超时回调。
 * - onCompletion():sse结束之后回调。
 */
@RestController
@RequestMapping("/sse")
@CrossOrigin(origins = "*")
public class SSEController {

    /**
     * 用于存储SseEmitter实例
     */
    private static ConcurrentHashMap<String, SseEmitter> sseCache = new ConcurrentHashMap<>();

    @RequestMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(String id) throws IOException {
        /**
         * 创建SseEmitter实例,并指定超时时间。默认情况下未设置,在这种情况下使用MVC Java Config
         * 或MVC命名空间中配置的默认值,或者如果未设置,则超时取决于底层服务器的默认值。
         */
        SseEmitter sseEmitter = new SseEmitter(180000L);
        sseCache.put(id, sseEmitter);
        // SSE超时回调
        sseEmitter.onTimeout(() -> {
            System.out.println("sse超时");
        });
        // 注册SSE异步请求完成时回调
        sseEmitter.onCompletion(() -> System.out.println("完成！！！"));
        // 发送消息,发送格式化为单个SSE“数据”行的对象
        sseEmitter.send("hello sse!");

        // 通过调度线程池每隔1s使用SseEmitter向客户端推送当前时间
        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(1);
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        pool.scheduleAtFixedRate(()->{
            try {
                sseEmitter.send(format.format(new Date()),MediaType.TEXT_PLAIN);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        },0,1000, TimeUnit.MILLISECONDS);
        return sseEmitter;
    }
}
