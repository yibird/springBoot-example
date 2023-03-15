package com.fly.domain.query;

import com.fly.common.query.Query;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 用户查询类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuery extends Query {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private Integer age;
    private Integer sex;
    private String phone;
    private String email;
    private String idCard;
    private String address;
    private Integer status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

}
