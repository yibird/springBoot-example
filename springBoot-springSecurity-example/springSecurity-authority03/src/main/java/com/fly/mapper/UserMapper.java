package com.fly.mapper;

import com.fly.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface UserMapper {
    User loadUserByUsername(@Param("username")String username);

    /**
     * 根据用户id查询对应的权限集合
     * @param uid 用户id
     * @return 用户所属角色对应的权限集合
     */
    List<String> getAuthorityByUid(@Param("uid")Integer uid);
}
