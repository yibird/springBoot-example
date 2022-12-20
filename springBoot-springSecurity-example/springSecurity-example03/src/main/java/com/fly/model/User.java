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
    private List<Role> roles = new ArrayList<>();

    /**
     * 返回授予用户的权限
     * @return 用户权限
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authoritySet = new HashSet<>();
        for (Role role : roles) {
            authoritySet.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
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
