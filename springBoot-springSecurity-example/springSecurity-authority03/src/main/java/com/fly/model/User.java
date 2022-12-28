package com.fly.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@TableName("sys_user")
public class User implements UserDetails {

    private Integer id;
    private String username;
    private String password;
    private Integer enable;
    private Integer accountNonExpired;
    private Integer accountNonLock;
    private Integer credentialsNonExpired;
    // 用户权限列表s
    private List<String> authorityList = new ArrayList<>();

    /**
     * 返回授予用户的权限
     * @return 用户权限
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authoritySet = new HashSet<>();
        /**
         * 从authorityList(权限列表)遍历读取权限添加至权限Set集合
         * 中并返回,Security根据权限Set集合进行授权
         */
        authorityList.stream().forEach(authority->{
            authoritySet.add(new SimpleGrantedAuthority(authority));
        });
        return authoritySet;
    }

    /**
     * 获取用户密码
     * @return 用户密码
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * 获取用户名
     * @return 用户名
     */
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired > 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLock > 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired > 0;
    }

    @Override
    public boolean isEnabled() {
        return enable > 0;
    }
}
