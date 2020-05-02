package com.fly.service;

import com.fly.entity.Users;

import java.util.List;

public interface UsersService {

    int addUsers(Users users);
    int upUsers(Users users);
    int delUsersById(Integer id);
    Users getUsersById(Integer id);
    List<Users> queryUsresListByUsers(Users users,int current,int size);
    int getTotal(Users users);
    int batchAddUsers(List<Users> usersList);
    int batchUpdateUsers(List<Users> usersList);
    int batchDelUsers(List<Integer> ids);

    int addData(Users users);
    int upData(Users users);
    Users getDataById(Integer id);
    List<Users> getDataAll();
}
