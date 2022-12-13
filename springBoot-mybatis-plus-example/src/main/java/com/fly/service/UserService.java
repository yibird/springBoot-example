package com.fly.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly.common.model.R;
import com.fly.common.page.PageResult;
import com.fly.domain.dto.UserDto;
import com.fly.domain.dto.UserLoginDto;
import com.fly.domain.model.User;
import com.fly.domain.query.UserQuery;
import com.fly.domain.vo.UserLoginVo;

import java.util.List;

public interface UserService extends IService<User> {
    List<User> getUserListAll();
    PageResult<List<User>> getUserList(UserQuery user);
    int addUser(UserDto userDto);
    int delUser(List<Long> ids);
    int updateUser(UserDto userDto);

    UserLoginVo login(UserLoginDto userLoginDto);
}
