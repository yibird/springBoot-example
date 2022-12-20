package com.fly.config;

import com.fly.mapper.UserMapper;
import com.fly.model.Role;
import com.fly.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 自定义UserDetailService实现类
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户名加载用户信息
     * @param username 用户名
     * @return 返回一个UserDetails对象
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.根据用户查询用户信息
        User user=userMapper.loadUserByUsername(username);
        if(ObjectUtils.isEmpty(user)) throw new UsernameNotFoundException("用户名错误");
        // 2.根据用户查询对应的权限信息
        List<Role> roles= userMapper.getRolesByUid(user.getId());
        // 设置权限
        user.setRoles(roles);
        return user;
    }
}
