package com.fly.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly.common.page.PageResult;
import com.fly.domain.dto.RoleDto;
import com.fly.domain.query.RoleQuery;
import com.fly.mapper.RoleMapper;
import com.fly.domain.model.Role;
import com.fly.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getListAll() {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        List<Role> roleList = roleMapper.selectPage(new Page<>(1, 2), wrapper).getRecords();
        roleMapper.selectCount(wrapper);
        return roleList;
    }

    @Override
    public PageResult<List<Role>> getList(RoleQuery role) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.like("role_name", role.getRoleName())
                .or()
                .like("role_key", role.getRoleKey())
                .or()
                .eq("id", role.getId())
                .and(i -> i.eq("status", role.getStatus()))
                .orderByAsc("order_number");
        Page<Role> rolePage = roleMapper.selectPage(new Page<>(role.getPage(), role.getLimit()), wrapper);
        return PageResult.of(rolePage.getRecords(), roleMapper.selectCount(wrapper));
    }

    // 由于是单表操作可以不添加事务,提示性能
    @Transactional
    @Override
    public int addRole(RoleDto roleDto) {
        Role role = new Role();
        // 将roleDto的属性拷贝到role,并忽略拷贝id属性
        BeanUtils.copyProperties(roleDto, role, "id");
        return roleMapper.insert(role);
    }

    @Override
    public int delRole(List<Integer> ids) {
        return roleMapper.deleteBatchIds(ids);
    }

    @Override
    public int updateRole(RoleDto roleDto) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        return roleMapper.updateById(role);
    }
}
