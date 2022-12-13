package com.fly.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly.common.model.R;
import com.fly.common.page.PageResult;
import com.fly.domain.dto.UserDto;
import com.fly.domain.dto.UserLoginDto;
import com.fly.domain.query.UserQuery;
import com.fly.domain.vo.UserLoginVo;
import com.fly.mapper.UserMapper;
import com.fly.domain.model.User;
import com.fly.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

// 指定slave数据源
@DS("slave")
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getUserListAll() {
        return userMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public PageResult<List<User>> getUserList(UserQuery user) {
        return PageResult.of(userMapper.getUserList(user), userMapper.getUserListCount(user));
    }

    @Override
    public int addUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return userMapper.insert(user);
    }

    @Override
    public int delUser(List<Long> ids) {
        return userMapper.deleteBatchIds(ids);
    }

    @Override
    public int updateUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return userMapper.updateById(user);
    }

    @Override
    public UserLoginVo login(UserLoginDto u) {
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.or(i -> i.eq("username", u.getUsername()).or().eq("email", u.getUsername()))
                .and(i -> i.eq("password", u.getPassword()));
        User user = userMapper.selectOne(wrapper);
        UserLoginVo userLoginVo = new UserLoginVo();
        if (null == user) {
            return null;
        }
        BeanUtils.copyProperties(userMapper.selectOne(wrapper), userLoginVo, "password");
        return userLoginVo;
    }
}
