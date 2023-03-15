package com.fly.controller.async;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class Hello {

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
