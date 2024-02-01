package com.fly;

import com.fly.service.impl.UserServiceImpl;
import com.fly.service.impl.UserTestServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = DynamicDataSourceApplication.class)
public class DynamicDataSourceTest {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserTestServiceImpl userTestService;

    @Test
    public void test() {
        List users = userTestService.getUsers();
        System.out.println("users:" + users);
//        Object firstUser = userTestService.getFirstUser();
//        System.out.println("firstUser:" + firstUser);
    }
}
