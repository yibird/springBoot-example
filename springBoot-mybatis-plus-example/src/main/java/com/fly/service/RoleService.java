package com.fly.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly.common.page.PageResult;
import com.fly.domain.dto.RoleDto;
import com.fly.domain.model.Role;
import com.fly.domain.query.RoleQuery;

import java.util.List;

public interface RoleService extends IService<Role> {

   List<Role> getListAll();

   PageResult<List<Role>> getList(RoleQuery roleQuery);

   int addRole(RoleDto roleDto);
   int delRole(List<Integer> ids);

   int updateRole(RoleDto roleDto);
}
