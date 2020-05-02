package com.fly;

import com.fly.dao.UsersDao;
import com.fly.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UsersTest {

    @Autowired
    private UsersDao usersDao;
    
    @Test
    public void add(){
        Users users=new Users()
                .setUname("mybatis-zxp111111")
                .setUno("mybatis-1000")
                .setAddress("地球")
                .setAge(123)
                .setSex(0)
                .setIdCard("12125313309056938")
                .setDes("他是个mybatis靓仔!");
        int row = usersDao.addUsers(users);
        System.out.println(row>0?"添加成功!":"添加失败!");
    }
    @Test
    public void update(){
        Users users=new Users()
                .setId(16)
                .setUname("mybatis-update-zxp111111")
                .setUno("mybatis-update-1000")
                .setAddress("地球123")
                .setAge(111)
                .setSex(1)
                .setIdCard("12125318724056938")
                .setDes("他是个mybatis-update靓仔!");
        int row=usersDao.upUsers(users);
        System.out.println(row>0?"修改成功!":"修改失败!");
    }
    
    @Test
    public void delete(){
        int row = usersDao.delUsersById(16);
        System.out.println(row>0?"删除成功!":"删除失败!");
    }
    @Test
    public void getData(){
        Users users = usersDao.getUsersById(1);
        System.out.println(users);
    }

    @Test
    public void getDataList(){
        Users users=new Users()
                .setUname("z");
        List<Users> usersList = usersDao.queryUsresListByUsers(users, 0, 2);
        for (Users users1 : usersList) {
            System.out.println(users1);
        }
        System.out.println("总条数为:"+usersDao.getTotal(users));;
    }
    @Test
    public void getDataListAll(){
        List<Users> usersList = usersDao.getDataAll();
        for (Users users : usersList) {
            System.out.println(users);
        }

    }
    
    @Test
    public void batchAdd(){
        List<Users> usersList=new ArrayList<>();
        Users users1=new Users()
                .setUname("users1")
                .setUno("users1-11")
                .setAddress("地球")
                .setAge(123)
                .setSex(0)
                .setIdCard("12345313309056938")
                .setDes("他是个mybatis靓仔!");
        Users users2=new Users()
                .setUname("users2")
                .setUno("users2-11")
                .setAddress("地球")
                .setAge(123)
                .setSex(0)
                .setIdCard("76235313309761338")
                .setDes("他是个mybatis靓仔!");
        usersList.add(users1);
        usersList.add(users2);
        int row = usersDao.batchAddUsers(usersList);
        System.out.println(row>0?"批量添加成功":"批量添加失败!");

    }

    @Test
    public void batchUpdate(){
        List<Users> usersList=new ArrayList<>();
        Users users1=new Users()
                .setId(18)
                .setUname("update-lalala")
                .setUno("mybatis-asdasd")
                .setAddress("地球123")
                .setAge(111)
                .setSex(1)
                .setIdCard("12145818724013938")
                .setDes("他是个mybatis-update靓仔!");;
        Users users2=new Users()
                .setId(19)
                .setUname("update-dododo")
                .setUno("mybatis-update-asdasasd")
                .setAddress("地球123")
                .setAge(111)
                .setSex(1)
                .setIdCard("12125651724056938")
                .setDes("他是个mybatis-update靓仔!");
        usersList.add(users1);
        usersList.add(users2);
        int row = usersDao.batchUpdateUsers(usersList);
        System.out.println(row>0?"批量修改成功":"批量修改失败!");
    }

    @Test
    public void batchDetele(){
        List<Integer> ids=new ArrayList<>();
        ids.add(18);
        ids.add(19);
        int row=usersDao.batchDelUsers(ids);
        System.out.println(row>0?"批量删除成功":"批量删除失败!");
    }

    /**
     * 注解方式操作数据
     */

    @Test
    public void addData(){
        Users users=new Users()
                .setUname("mybatis-zxp111111")
                .setUno("mybatis-1000")
                .setAddress("地球")
                .setAge(123)
                .setSex(0)
                .setIdCard("14356313309056938")
                .setDes("他是个mybatis靓仔!");
        int row=usersDao.addData(users);
        System.out.println(row>0?"添加成功！":"添加失败!");
    }

    @Test
    public void updateData(){
        Users users=new Users()
                .setId(20)
                .setUname("asdbjqweq123")
                .setUno("mybatis-update-1000")
                .setAddress("地球123")
                .setAge(111)
                .setSex(1)
                .setIdCard("12125315134056938")
                .setDes("他是个mybatis-update靓仔!");
        int row=usersDao.upData(users);
        System.out.println(row>0?"修改成功！":"修改失败!");
    }
    
    @Test
    public void queryDataById(){
        Users users = usersDao.getDataById(1);
        System.out.println(users);
    }
    @Test
    public void getDataAll(){
        List<Users> usersList = usersDao.getDataAll();
        for (Users users : usersList) {
            System.out.println(users);
        }

    }
}
