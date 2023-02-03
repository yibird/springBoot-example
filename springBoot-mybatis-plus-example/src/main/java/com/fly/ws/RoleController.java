package com.fly.ws;

import com.fly.common.model.R;
import com.fly.domain.dto.RoleDto;
import com.fly.domain.query.RoleQuery;
import com.fly.service.RoleService;
import com.fly.utils.AssertUtil;
import com.fly.utils.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/getRoleListAll")
    public R getRoleListAll() {
        return R.ok(roleService.getListAll());
    }

    @GetMapping("/getRoleList")
    public R getRoleList(@RequestBody RoleQuery roleQuery) {
        System.out.println("role:" + roleQuery);
        return R.ok(roleService.getList(roleQuery));
    }


    /**
     * 添加角色
     *
     * @param roleDto 角色Dto对象
     * @return R
     * 请求参数:{"roleName": "测试","roleKey": "test","status": 1,"orderNumber": 999}
     */
    @PostMapping("/add")
    public R addRole(@RequestBody RoleDto roleDto) {
        return R.ok(roleService.addRole(roleDto) > 0 ? "添加成功" : "添加失败");
    }


    /**
     * 刪除角色
     *
     * @param ids 角色id数组
     * @return R
     */
    @PostMapping("/del")
    public R delRole(@RequestParam("ids") Integer... ids) {
        AssertUtil.isTrue(ids == null || ids.length == 0, "id参数错误");
        int row = roleService.delRole(ListUtil.itemsToList(ids));
        return R.ok(row > 0 ? "删除成功" : "删除失败");
    }

    /**
     * 根据角色id修改角色
     *
     * @param roleDto 角色Dto对象
     * @return R
     * 请求参数:{"id": 5,"roleName": "老师","roleKey": "teacher","status": 1,"orderNumber": 999}
     */
    @PostMapping("/update")
    public R update(@RequestBody RoleDto roleDto) {
        return R.ok(roleService.updateRole(roleDto) > 0 ? "修改成功" : "修改失败");
    }
}
