package com.fly.domain.vo;

import com.fly.common.model.BaseModel;
import lombok.Data;

import java.util.Date;

/**
 * 用户登录Vo对象, UserLoginVo应禁止返回password字段
 */
@Data
public class UserLoginVo extends BaseModel {
    private Long id;
    private String username;
    private String nickname;
    private Integer age;
    private Integer sex;
    private String phone;
    private String email;
    // @TableField用于指定表字段,exist表示是否是数据表字段
    private String idCard;
    private String avatar;
    private String address;
    private Integer status;
    private Date birthday;
}
