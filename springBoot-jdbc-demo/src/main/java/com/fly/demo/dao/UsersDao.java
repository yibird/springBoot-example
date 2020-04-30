package com.fly.demo.dao;

import com.fly.demo.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository //它用于将数据访问层 (DAO 层 ) 的类标识为 Spring Bean
public class UsersDao {

    /**
     * jdbc操作模板类
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * NamedParameterJdbcTemplate类是基于JdbcTemplate类并对它进行了
     * 封装从而支持命名(具名)参数特性,例如: :+参数名 这种方式就是具名参数,
     * 最后生成的sql会将sql中的具名参数替换成实际的值
     */
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    /**
     * 添加data
     * @param users
     * @return
     */
    public int add(Users users){
        String sql="insert into users(uName,uNo,sex,age,idCard,address,des) " +
                "values(:uName,:uNo,:sex,:age,:idCard,:address,:des);";
        /**
         * MapSqlParameterSource:sql参数映射源用于外部向jdbc内部的参数传递
         */
        MapSqlParameterSource ps=new MapSqlParameterSource();
        ps.addValue("uName",users.getUname());
        ps.addValue("uNo",users.getUno());
        ps.addValue("sex",users.getSex());
        ps.addValue("age",users.getAge());
        ps.addValue("address",users.getAddress());
        ps.addValue("idCard",users.getIdCard());
        ps.addValue("des",users.getDes());
        return namedParameterJdbcTemplate.update(sql,ps);
    }

    /**
     * 删除
     */
    public int delUsers(Users users){
        String sql="delete from users where id=:id ";
        MapSqlParameterSource ps=new MapSqlParameterSource();
        ps.addValue("id",users.getId());
        ps.addValue("uName",users.getUname());
        ps.addValue("uNo",users.getUno());
        ps.addValue("sex",users.getSex());
        ps.addValue("age",users.getAge());
        ps.addValue("address",users.getAddress());
        ps.addValue("idCard",users.getIdCard());
        ps.addValue("des",users.getDes());
        return namedParameterJdbcTemplate.update(sql,ps);
    }

    /**
     * 修改
     */
    public int upUsers(Users users){
        String sql="update  users set uName=:uName,uNo=:uNo,sex=:sex," +
                "age=:age,idCard=:idCard,address=:address,des=:des where id=:id";
        MapSqlParameterSource ps=new MapSqlParameterSource();
        ps.addValue("id",users.getId());
        ps.addValue("uName",users.getUname());
        ps.addValue("uNo",users.getUno());
        ps.addValue("sex",users.getSex());
        ps.addValue("age",users.getAge());
        ps.addValue("address",users.getAddress());
        ps.addValue("idCard",users.getIdCard());
        ps.addValue("des",users.getDes());
        return namedParameterJdbcTemplate.update(sql,ps);
    }

    /**
     * 查询单条数据
     */
    public Users getUsersById(Integer id){
        String sql="select * from users where id=:id";
        MapSqlParameterSource ps=new MapSqlParameterSource();
        ps.addValue("id",id);
        RowMapper<Users> rowMapper = BeanPropertyRowMapper.newInstance(Users.class);
        return namedParameterJdbcTemplate.queryForObject(sql,ps,rowMapper);
    }

    /**
     * 查询集合
     */
    public List<Users> queryUsersList(){
        String sql="select * from users";

        /**
         * RowMapper对象:RowMapper可以将查询出的每一行数据封装成一个用户自定义的类,
         */
        return namedParameterJdbcTemplate.query(sql, new RowMapper<Users>() {
            //ResultSet 就是每一行数据的集合,通过访问不同列名就可以获得数据
            @Override
            public Users mapRow(ResultSet rs, int i) throws SQLException {
                Users users = new Users();
                users.setId(rs.getInt("id"))
                        .setUname(rs.getString("uName"))
                        .setUno(rs.getString("uNo"))
                        .setAge(rs.getInt("age"))
                        .setSex(rs.getInt("sex"))
                        .setIdCard(rs.getString("idCard"))
                        .setDes(rs.getString("des"));
                return users;
            }
        });
    }

    /**
     * 多条件查询List
     */
    public List<Users> queryUsersListByCondition(Users users){
       StringBuilder sb=new StringBuilder();
        sb.append("select * from users where 1=1 ");
        MapSqlParameterSource ps=new MapSqlParameterSource();

        if(!StringUtils.isEmpty(users.getUname())){
            sb.append("and uName like '%' :uName '%'");
            ps.addValue("uName",users.getUname());
        }
        if(!StringUtils.isEmpty(users.getUno())){
            sb.append("and uNo=:uNo");
            ps.addValue("uNo",users.getUno());
        }
        if(!StringUtils.isEmpty(users.getSex())){
            sb.append("and sex=:sex");
            ps.addValue("sex",users.getSex());
        }
        if(!StringUtils.isEmpty(users.getAge())){
            sb.append("and age=:age");
            ps.addValue("age",users.getAge());
        }
        return namedParameterJdbcTemplate.query(sb.toString(),ps,BeanPropertyRowMapper.newInstance(Users.class));
    }

    /**
     * 多条件查询总条数
     */
    public int queryUsersListByCount(Users users){
        StringBuilder sb=new StringBuilder();
        sb.append("select count(1) from users where 1=1 ");
        MapSqlParameterSource ps=new MapSqlParameterSource();

        if(!StringUtils.isEmpty(users.getUname())){
            sb.append("and uName like '%' :uName '%'");
            ps.addValue("uName",users.getUname());
        }
        if(!StringUtils.isEmpty(users.getUno())){
            sb.append("and uNo=:uNo");
            ps.addValue("uNo",users.getUno());
        }
        if(!StringUtils.isEmpty(users.getSex())){
            sb.append("and sex=:sex");
            ps.addValue("sex",users.getSex());
        }
        if(!StringUtils.isEmpty(users.getAge())){
            sb.append("and age=:age");
            ps.addValue("age",users.getAge());
        }
        return namedParameterJdbcTemplate.queryForObject(sb.toString(),ps,Integer.class).intValue();
    }


    /**
     * 批量添加
     */
    public int batchAdd(List<Users> usersList){
        String sql="insert into users(uName,uNo,sex,age,idCard,address,des) " +
                "values(:uname,:uno,:sex,:age,:idCard,:address,:des);";

        try{
            //通过SqlParameterSourceUtils.createBatch方法将参数构建成SqlParameterSource数组
            SqlParameterSource[] ps= SqlParameterSourceUtils.createBatch(usersList.toArray());
            namedParameterJdbcTemplate.batchUpdate(sql,ps);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 1;

    }

    /**
     * 批量删除
     */
    public int batchDel(List<Users> usersList){
        String sql="delete from users where id=:id";
       try{
           SqlParameterSource[] ps=SqlParameterSourceUtils.createBatch(usersList.toArray());
           namedParameterJdbcTemplate.batchUpdate(sql,ps);
       }catch (Exception e){
           e.printStackTrace();
           return 0;
       }
        return 1;
    }

    /**
     * 批量修改
     */
    public int batchUp(List<Users> usersList){
        String sql="update  users set uName=:uname,uNo=:uno,sex=:sex," +
                "age=:age,idCard=:idCard,address=:address,des=:des where id=:id";
        try{
            SqlParameterSource[] ps=SqlParameterSourceUtils.createBatch(usersList.toArray());
            namedParameterJdbcTemplate.batchUpdate(sql,ps);
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
}
