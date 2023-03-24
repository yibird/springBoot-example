package com.fly;

import com.fly.controller.UserController;
import com.fly.domain.dto.UserDto;
import com.fly.domain.model.User;
import com.fly.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.lang.invoke.LambdaMetafactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description DesensitizationBenchmarkTest
 * @Author zchengfeng
 * @Date 2023/3/17 9:20
 */
@SpringBootTest(classes = ApplicationServer.class)
public class DesensitizationBenchmarkTest {

    @Autowired
    UserService userService;
    @Autowired
    UserController userController;

//    @BeforeEach
//    @Test
//    public void initUserList() {
//        for (int i = 0; i < 10000; i++) {
//            UserDto user = new UserDto();
//            user.setId((long) (i + 1))
//                    .setUsername("name_" + (i + 1))
//                    .setPassword("123456")
//                    .setNickname("nikeName_" + (i + 1))
//                    .setAge(20)
//                    .setSex(i % 2 == 0 ? 0 : 1)
//                    .setPhone("17622221111")
//                    .setEmail("17622221111@qq.com")
//                    .setIdCard("431203200209096931")
//                    .setAvatar("https://xxxx")
//                    .setAddress("广东省鸡城市南城区阳光路88号")
//                    .setStatus(1)
//                    .setBirthday(new Date())
//                    .setBankCard("6221273779582333402");
//            userService.addUser(user);
//        }
//    }

    /**
     * Mybatis Interceptor脱敏数据性能测试(由于Mybatis有缓存,应该排除第一次查询结果)
     * 数据量    耗时(ms)
     * 10000    1127
     */
    @Test
    public void benchmarkTest01() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getId = User.class.getMethod("getId");
        User user = new User();
        user.setId(1l);
        Long invoke = (Long) getId.invoke(user);
        System.out.println("invoke");
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        userController.getUserListAll();
//        stopWatch.stop();
//        System.out.println("耗时(ms):" + stopWatch.getTotalTimeMillis());
    }

    /**
     * Fastjson 作为SpringMVC MessageConverter实现数据脱敏
     * 数据量    耗时(ms)
     * 10000    678
     */
    @Test
    public void benchmarkTest02() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        userController.getUserListAll();
        stopWatch.stop();
        System.out.println("耗时(ms):" + stopWatch.getTotalTimeMillis());
    }

    @Test
    public void benchmarkTest03() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        userController.getUserListAll();
        stopWatch.stop();
        System.out.println("耗时(ms):" + stopWatch.getTotalTimeMillis());
    }
}
