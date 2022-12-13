package com.fly.domain.query;

import com.fly.common.query.Query;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 角色查询参数类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleQuery extends Query {
    private Integer id;
    private String roleName;
    private String roleKey;
    private Integer status;
    private Integer orderNumber;
}
