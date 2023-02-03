package com.fly.ws;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/list")
    public String list(){
        return "get user list success";
    }

    /**
     * 由于Security的权限列表从根据用户所属角色对应的菜单表(sys_menu)读取,
     * 因此判断的权限必须与sys_menu表中menu_key字段一致
     * @return
     */
    @PreAuthorize("hasAuthority('user_query')")
    @GetMapping("/getListByMap")
    public String getListByMap(){
        return "getListByMap user list success";
    }

    @PreAuthorize("hasAuthority('user_add')")
    @PostMapping("/add")
    public String add(){
        return "add user success";
    }

    @PreAuthorize("hasAuthority('user_update')")
    @PostMapping("/update")
    public String update(){
        return "update user success";
    }

    @PreAuthorize("hasAuthority('user_del')")
    @PostMapping("/del")
    public String del(){
        return "del user success";
    }
}
