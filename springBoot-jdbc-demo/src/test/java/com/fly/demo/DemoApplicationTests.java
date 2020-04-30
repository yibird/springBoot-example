package com.fly.demo;

import com.fly.demo.dao.UsersDao;
import com.fly.demo.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class DemoApplicationTests {


    @Autowired
    UsersDao usersDao;

    @Test
    public void addData(){
        Users users=new Users();
        users.setUname("zxp666888")
                .setUno("z1001")
                .setSex(0)
                .setAge(22)
                .setAddress("中国")
                .setIdCard("464453199809051938")
                .setDes("他是一个帅哥");
        int row = usersDao.add(users);
        System.out.println(row>0?"添加成功!":"添加失败!");
    }

    @Test
    public void delData(){
        Users users=new Users().setId(6);
        int row = usersDao.delUsers(users);
        System.out.println(row>0?"删除成功!":"删除失败!");
    }
    @Test
    public void upData(){
        Users users=new Users().setUname("update-zxp")
                .setId(1)
                .setAge(1000)
                .setSex(1)
                .setUno("u-1111")
                .setIdCard("421103199809526938")
                .setAddress("中国xxx");
        int row=usersDao.upUsers(users);
        System.out.println(row>0?"修改成功!":"修改失败!");
    }

    /**
     * 查询所有数据
     */
    @Test
    public void queryData(){
        List<Users> users = usersDao.queryUsersList();
        for (Users user : users) {
            System.out.println(user);
        }
    }

    /**
     * 根据条件查询集合
     */
    @Test
    public void queryDataByContent(){
        Users users=new Users().setUname("z");
        List<Users> usersList = usersDao.queryUsersListByCondition(users);
        for (Users user : usersList) {
            System.out.println(user);
        }
    }

    /**
     * 根据条件查询总记录数
     */
    @Test
    public void queryDataCount(){
        Users users=new Users().setUname("z");
        final int count = usersDao.queryUsersListByCount(users);
        System.out.println("总记录数为:"+count);
    }

    /**
     * 批量添加
     */
    @Test
    public void batchAddData(){
        Users users1=new Users()
                .setUname("batch-add-zxp1")
                .setUno("b1123")
                .setSex(0)
                .setAge(100)
                .setAddress("中国南方")
                .setIdCard("971103199809056938")
                .setDes("他是个孩子啊!");
        Users users2=new Users()
                .setUname("batch-add-zxp2")
                .setUno("b2223")
                .setSex(1)
                .setAge(88)
                .setAddress("中国西方")
                .setIdCard("621103199809056938")
                .setDes("他是个狗子啊!");
        List<Users> usersList=new ArrayList<>();
        usersList.add(users1);
        usersList.add(users2);
        int row = usersDao.batchAdd(usersList);
        System.out.println(row>0?"批量添加成功!":"批量添加异常!");
    }

    /**
     * 批量删除
     */
    @Test
    public void batchDelData(){
        Users u1=new Users().setId(2);
        Users u2=new Users().setId(3);
        List<Users> usersList=new ArrayList<>();
        usersList.add(u1);
        usersList.add(u2);
        int row=usersDao.batchDel(usersList);
        System.out.println(row>0?"批量删除成功!":"批量删除异常!");
    }

    /**
     * 批量修改
     */
    @Test
    public void batchUpData(){
        Users u1=new Users().setId(1)
                .setUname("batchUpData-zxp")
                .setUno("b-123123")
                .setAge(777)
                .setIdCard("783103195209056938")
                .setSex(0);
        Users u2=new Users().setId(4)
                .setUname("batchUpData-123123p")
                .setUno("b-456456")
                .setIdCard("783103259809056938")
                .setAge(89)
                .setSex(1);
        List<Users> usersList=new ArrayList<>();
        usersList.add(u1);
        usersList.add(u2);
        int row = usersDao.batchUp(usersList);
        System.out.println(row>0?"批量修改成功!":"批量修改异常!");
    }


}
