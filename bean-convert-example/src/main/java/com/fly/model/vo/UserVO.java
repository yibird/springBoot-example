package com.fly.model.vo;

import lombok.*;

import java.util.Date;

@Data
@ToString
public class UserVO {
    private Integer id;
    private String username;
    private String nickname;
    private String sex;
    private String address;
    private Date birthday;
    private String avatar;
}
