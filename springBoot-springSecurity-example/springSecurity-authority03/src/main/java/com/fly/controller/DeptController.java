package com.fly.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dept")
public class DeptController {
    @GetMapping("/list")
    public String list(){
        return "get dept list success";
    }

    @PreAuthorize("hasAuthority('dept_query')")
    @GetMapping("/getListByMap")
    public String getListByMap(){
        return "getListByMap dept list success";
    }

    @PreAuthorize("hasAuthority('dept_add')")
    @PostMapping("/add")
    public String add(){
        return "add dept success";
    }

    @PreAuthorize("hasAuthority('dept_update')")
    @PostMapping("/update")
    public String update(){
        return "update dept success";
    }

    @PreAuthorize("hasAuthority('dept_del')")
    @PostMapping("/del")
    public String del(){
        return "del dept success";
    }
}
