package com.fly.service.impl;

import com.fly.dao.UsersDao;
import com.fly.entity.Users;
import com.fly.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    UsersDao usersDao;

    @Override
    public int addUsers(Users users) {

        return usersDao.addUsers(users);
    }

    @Override
    public int upUsers(Users users) {

        return usersDao.upUsers(users);
    }

    @Override
    public int delUsersById(Integer id) {
        return usersDao.delUsersById(id);
    }

    @Override
    public Users getUsersById(Integer id) {
        return usersDao.getUsersById(id);
    }

    @Override
    public List<Users> queryUsresListByUsers(Users users, int current, int size) {
        return usersDao.queryUsresListByUsers(users,current,size);
    }

    @Override
    public int getTotal(Users users) {
        return usersDao.getTotal(users);
    }

    @Override
    public int batchAddUsers(List<Users> usersList) {
        return  usersDao.batchAddUsers(usersList);
    }

    @Override
    public int batchUpdateUsers(List<Users> usersList) {

        return usersDao.batchUpdateUsers(usersList);
    }

    @Override
    public int batchDelUsers(List<Integer> ids) {
        return usersDao.batchDelUsers(ids);
    }

    @Override
    public int addData(Users users) {

        return usersDao.addData(users);
    }

    @Override
    public int upData(Users users) {
        return usersDao.upUsers(users);
    }

    @Override
    public Users getDataById(Integer id) {
        return usersDao.getDataById(id);
    }

    @Override
    public List<Users> getDataAll() {
        return usersDao.getDataAll();
    }
}
