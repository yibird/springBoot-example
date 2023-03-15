package com.fly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description HelloController
 * @Author zchengfeng
 * @Date 2023/3/13 11:03
 */
@RestController
public class HelloController {

    @Autowired
    SpringWebSocketHandle socketHandle;

    @GetMapping("/hello")
    public void hello(){
        System.out.println("hello");
        socketHandle.send("hello message");
    }
}
