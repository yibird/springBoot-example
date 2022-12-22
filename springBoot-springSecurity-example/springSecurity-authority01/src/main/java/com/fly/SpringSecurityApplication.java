package com.fly;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class);
    }
    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }
    @GetMapping("/test")
    public String test(){
        return "admin";
    }
    @GetMapping("/user")
    public String user(){
        return "user";
    }
    @GetMapping("/userList")
    public String userList(){
        return "userList";
    }
}