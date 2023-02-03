package com.fly.ws;


import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", methods = {
        RequestMethod.GET,
        RequestMethod.POST,
        RequestMethod.DELETE,
        RequestMethod.PUT,
        RequestMethod.OPTIONS
}, allowCredentials = "true", maxAge = 3600, allowedHeaders = "*")
public class CrossController {


    @CrossOrigin(origins = "*", methods = {
            RequestMethod.GET,
    }, allowCredentials = "true", maxAge = 3600, allowedHeaders = "*")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
