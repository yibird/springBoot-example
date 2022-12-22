package com.fly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class SpringSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class);
    }

    /**
     * @return
     * @PreAuthorize("hasRole('ADMIN') and authentication.name=='admin'")
     * 表示执行方法前身份验证登录为 admin 并且 admin用户具有ADMIN权限才能访问
     */
    @PreAuthorize("hasRole('ADMIN') and authentication.name=='admin'")
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    /**
     * @param name
     * @return
     * @PreAuthorize("authentication.name==#name")表示执行方法后 认证信息的登录名和请求参数name相等才能授权返回。
     */
    @PreAuthorize("authentication.name==#name")
    @GetMapping("/hello")
    public String hello(String name) {
        return "hello:" + name;
    }

    /**
     * @param users
     * @return str
     * @PreFilter(value = "filterObject.id%2!=0", filterTarget = "users")
     * 表示过滤users集合中 id % 2的参数,filterObject表示过滤对象。
     * filterTarget:表示应过滤的参数的名称(必须是非空集合实例)如果方法包含
     * 单个集合参数,则可以省略此属性。
     * <p>
     * 请求参数:
     */
    @PreFilter(value = "filterObject.id%2!=0", filterTarget = "users")
    @GetMapping("/users")
    public String users(@RequestBody List<Object> users) {
        System.out.println("asdasdasd");
        return "users";
    }

    /**
     * @PostAuthorize("returnObject=='admin'")表示在执行方法后根据方法返回值
     * 是否等于admin来决定是否授权返回,若不满足条件请求则403,returnObject表示执行返回的
     * 返回值。
     * @param id 请求id
     * @return object
     */
    @PostAuthorize("returnObject=='admin'")
    @GetMapping("/getObjectById")
    public Object getObjectById(Integer id) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "admin");
        map.put(2, "test");
        map.put(3, "user");
        return map.get(id);
    }

    /**
     * @PostFilter("filterObject.get('id')%2==0")表示在方法执行后的以id属性 % 2=0
     * 的条件过滤执行结果,filterObject表示执行方法的返回值,
     * 响应List为:[{"name":"name_2","id":2},{"name":"name_4","id":4}]
     * @return
     */
    @PostFilter("filterObject.get('id')%2==0")
    @GetMapping("/getUserList")
    public List<Map<String, Object>> getUserList(){
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i);
            map.put("name", "name_"+i);
            list.add(map);
        }
        return list;
    }
}