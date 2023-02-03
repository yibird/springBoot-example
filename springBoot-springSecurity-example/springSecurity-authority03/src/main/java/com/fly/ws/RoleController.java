package com.fly.ws;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {
    @GetMapping("/list")
    public String list(){
        return "get role list success";
    }

    @PreAuthorize("hasAuthority('role_query')")
    @GetMapping("/getListByMap")
    public String getListByMap(){
        return "getListByMap role list success";
    }

    @PreAuthorize("hasAuthority('role_add')")
    @PostMapping("/add")
    public String add(){
        return "add role success";
    }
    @PreAuthorize("hasAuthority('role_update')")
    @PostMapping("/update")
    public String update(){
        return "update role success";
    }

    @PreAuthorize("hasAuthority('role_del')")
    @PostMapping("/del")
    public String del(){
        return "del role success";
    }
}
