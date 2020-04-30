package com.fly.demo.controller;

import com.fly.demo.dao.UsersDao;
import com.fly.demo.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private UsersDao usersDao;

    @RequestMapping("/hello")
    public String hello(){

        return "hello";
    }

    @RequestMapping("/get")
    public List<Users> getList(){
        return  usersDao.queryUsersList();
    }
}
