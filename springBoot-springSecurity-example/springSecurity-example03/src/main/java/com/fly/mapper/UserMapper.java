package com.fly.mapper;


import com.fly.model.Role;
import com.fly.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    User loadUserByUsername(@Param("username")String username);

    List<Role> getRolesByUid(@Param("uid")Integer uid);

}
