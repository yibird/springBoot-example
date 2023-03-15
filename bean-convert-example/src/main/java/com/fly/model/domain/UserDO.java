package com.fly.model.domain;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@ToString
@Accessors(chain = true)
public class UserDO {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private int sex;
    private int age;
    private Integer roleId;
    private String address;
    private Date birthday;
    private String avatar;
}
