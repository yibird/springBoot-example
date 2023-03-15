package com.fly.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly.domain.model.User;
import com.fly.domain.query.UserQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> getUserList(@Param("u") UserQuery user);
    Long getUserListCount(@Param("u") UserQuery user);
}
