package com.fly.service.impl;

import com.fly.ds.annotation.DS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author zchengfeng
 * @Date 2024/1/25 17:43:25
 */

@Service
public class UserTestServiceImpl {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 如果不使用@DS注解或未指定数据源名称,则使用默认数据源
     *
     * @return 返回用户列表
     */
    public List getUsers() {
        return jdbcTemplate.queryForList("select * from user");
    }

    /**
     * 指定使用slave数据源查询第一个用户
     *
     * @return 第一个用户
     */
    @DS("slave")
    public Object getFirstUser() {
        return jdbcTemplate.queryForList("select * from user limit 1");
    }
}
