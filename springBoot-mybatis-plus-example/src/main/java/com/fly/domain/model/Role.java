package com.fly.domain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fly.common.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_role")
public class Role extends BaseModel {
    private Integer id;
    private String roleName;
    private String roleKey;
    private Integer status;
    private Integer orderNumber;
}
