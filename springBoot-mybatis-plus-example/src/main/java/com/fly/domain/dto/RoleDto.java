package com.fly.domain.dto;

import lombok.Data;

/**
 * Role DTO对象
 */
@Data
public class RoleDto {
    private Integer id;
    private String roleName;
    private String roleKey;
    private Integer status;
    private Integer orderNumber;
}
