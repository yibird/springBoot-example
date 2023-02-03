package com.fly.ws.string;

import com.fly.constant.RedisKeyPrefixConstant;
import com.fly.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis String类型操作Controller,提供String类型应用场景
 */
@RestController
@RequestMapping("/string")
public class StringController {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 应用场景:分布式ID生成器
     *
     * @param prefix 是否添加业务前缀,默认false,
     * @return id
     */
    @GetMapping("/idGenerator")
    public Object idGenerator(@RequestParam(value = "prefix", required = false) boolean prefix) {
        ValueOperations<String, Long> ops = redisTemplate.opsForValue();
        Long randomNum = RandomUtil.nextLong();
        String key = prefix ?
                RedisKeyPrefixConstant.id.getPrefix() + RandomUtil.nextLong()
                : String.valueOf(randomNum);
        if (redisTemplate.hasKey(key)) {
            return ops.get(key);
        }
        ops.set(key, randomNum);
        return "key:" + key + ",value:" + randomNum;
    }

    /**
     * 分布式id,支持id自增、自减
     * 注意:只有在Redis String类型中只有存储整数才能自增自减,对于非整数类型
     * 自增自减操作会出现错误。
     *
     * @param key 访问key
     * @param op  操作符
     * @return 返回自增自减后的结果值或验证信息
     */
    @GetMapping("idGeneratorOps")
    public String idGeneratorOps(@RequestParam String key,
                                 @RequestParam String op) {
        if (key.length() == 0 || op.length() == 0) {
            return "请输入key或操作符";
        }
        ValueOperations<String, Long> ops = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(key)) {
            switch (op) {
                case "increment":
                    ops.increment(key, 1L);
                    return String.valueOf(ops.get(key));
                case "decrement":
                    ops.decrement(key, 1L);
                    return String.valueOf(ops.get(key));
                default:
                    return "非法操作符";
            }
        }
        return "key不存在";
    }


    /**
     * 应用场景:计数器
     * 说明:计数器用于统计ip的访问次数,如果redis中不存在访问ip,
     * 则将访问ip做为key,value默认为1进行存储,否则每次访问的ip对应的计数器就会+1
     *
     * @return
     */
    @GetMapping("/counter")
    public String counter(@RequestParam("ipAddr") String ipAddr) {
        ValueOperations<String, Long> ops = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(ipAddr)) {
            ops.increment(ipAddr, 1L);
        } else {
            ops.set(ipAddr, 1L);
        }
        return "ip:" + ipAddr + "访问次数:" + ops.get(ipAddr);
    }

    /**
     * 应用场景:限流器
     * 说明:统计ip的访问频率,若在5分钟内访问次数超过指定阈值,则将请求排队或拒绝请求,
     * 否则正常放行请求。例如1分钟内 访问次数 <= 3则正常放行,访问次数 > 3
     * 则直接拒绝请求。
     * <p>
     * 注意:以访问ip作为key的方式可能会生成大量key,不易管理且性能消耗高,因为每次生成key Redis
     * 都需要进行内存分配,一般推荐将ip以hashMap存储,ip地址为hashKey,访问次数为value,
     * 这样可以避免大量key内存分配。
     */
    @GetMapping("/limiter")
    public String limiter(@RequestParam("ipAddr") String ipAddr) {
        ValueOperations<String, Integer> ops = redisTemplate.opsForValue();
        String key = RedisKeyPrefixConstant.ip.getPrefix() + ipAddr;
        String result = "ip:" + ipAddr + "正常放行";
        if (redisTemplate.hasKey(key)) {
            System.out.println("ops.get(key):" + ops.get(key));
            // 判断ip访问次数是否超过3次,由于先比较后自增,判断条件为 > 2
            if (ops.get(key) > 2) {
                return "ip超出访问次数,拒绝访问!";
            }
            ops.increment(key, 1);
            return result;
        } else {
            // 设置key-value过期时间为1分钟
            ops.set(key, 1, 1, TimeUnit.MINUTES);
        }
        return result;
    }

    /**
     * 应用场景:分布式缓存(简单使用),模拟缓存用户
     */
    @GetMapping("/cache")
    public String cache(@RequestParam Integer id) {

        ValueOperations<String, List<Map<String, Object>>> ops = redisTemplate.opsForValue();
        String key = RedisKeyPrefixConstant.cache.getPrefix() + id;
        /**
         * 先从缓存中读取数据,若命中缓存则直接返回缓存中的数据,否则查询数据源并将查询结果存储在redis
         * 下次访问根据key可以命中缓存,无需访问数据源,从而提升性能
         */
        List<Map<String, Object>> cacheResult = ops.get(key);
        if (cacheResult != null) {
            return "命中缓存,result:" + cacheResult.toString();
        }

        List<Map<String, Object>> userList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Map<String, Object> userModel = new HashMap<>();
            userModel.put("id", i);
            userModel.put("age", i);
            userModel.put("sex", i);
            userModel.put("address", "shanghai" + i);
            userList.add(userModel);
        }
        List<Map<String, Object>> result = userList.stream()
                .filter(item -> item.get("id") == id)
                .collect(Collectors.toList());
        if (result.isEmpty()) {
            return "记录不存在";
        }
        // 未命中缓存则缓存结果
        ops.set(key, result);
        return "未命中缓存,result:" + result.toString();
    }

    /**
     * 应用场景:实现锁机制
     * @return
     */
    @GetMapping("lock")
    public String lock() {
        return "";
    }

    /**
     * 日志追加功能
     *
     * @return
     */
    @GetMapping("/logger")
    public String logger() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.append("\n","\nasdasd");
        return "";
    }
}
