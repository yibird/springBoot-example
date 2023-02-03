//package com.fly.model;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.Data;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.util.Date;
//
//@Data
//@TableName("sys_user")
//public class User extends BaseModel {
//    @TableId(type = IdType.AUTO)
//    private Long id;
//    private String username;
//    private String password;
//    private String nickname;
//    private Integer age;
//    private Integer sex;
//    private String phone;
//    private String email;
//    @TableField(value = "idcard", exist = true)
//    private String idCard;
//    private String avatar;
//    private String address;
//    private Integer status;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date birthday;
//    private String remark;
//}
