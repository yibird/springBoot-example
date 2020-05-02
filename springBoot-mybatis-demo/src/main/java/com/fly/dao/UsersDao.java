package com.fly.dao;

import com.fly.entity.Users;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UsersDao {

    /**
     * 通过xml的形式操作数据
     */
    int addUsers(@Param("users") Users users);
    int upUsers(@Param("users")Users users);
    int delUsersById(@Param("id")Integer id);
    Users getUsersById(@Param("id")Integer id);
    List<Users> queryUsresListByUsers(@Param("users")Users users,@Param("current") int current,@Param("size")int size);
    int getTotal(@Param("users")Users users);
    int batchAddUsers(@Param("usersList") List<Users> usersList);
    int batchUpdateUsers(@Param("usersList") List<Users> usersList);
    int batchDelUsers(@Param("ids")List<Integer> ids);

    /**
     * 通过注解的形式操作数据
     */

    @Insert("insert into users(uName,uNo,sex,age,idCard,address,des)"+
            "values(#{users.uname},#{users.uno},#{users.sex},#{users.age},#{users.idCard}," +
            "#{users.address},#{users.des});")
    int addData(@Param("users") Users users);

    @Update("update  users set uName=#{users.uname},uno=#{users.uno},sex=#{users.sex}," +
            "age=#{users.age},idCard=#{users.idCard},address=#{users.address},des=#{users.des}" +
            "where id=#{users.id}")
    int upData(@Param("users") Users users);

    @Select("select * from users where id=#{id}")
    Users getDataById(@Param("id")Integer id);

    @Select({"<script>select * from users</script>"})
    List<Users> getDataAll();


}
