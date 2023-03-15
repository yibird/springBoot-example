package com.fly;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fly.mapper.RoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ApplicationServer.class)
public class RunTest {

    @Autowired
    RoleMapper roleMapper;

    @Test
    public void test01(){
        System.out.println("asdasda");
        System.out.println("roleMapper:"+roleMapper.selectList(new QueryWrapper<>()));
    }
}
