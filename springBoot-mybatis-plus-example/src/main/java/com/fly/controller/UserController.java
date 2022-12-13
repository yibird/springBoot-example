package com.fly.controller;

import com.fly.common.model.R;
import com.fly.common.query.Query;
import com.fly.domain.dto.UserDto;
import com.fly.domain.dto.UserLoginDto;
import com.fly.domain.query.UserQuery;
import com.fly.domain.vo.UserLoginVo;
import com.fly.service.UserService;
import com.fly.utils.AssertUtil;
import com.fly.utils.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public R login(@RequestBody UserLoginDto userLoginDto) {
        UserLoginVo user = userService.login(userLoginDto);
        if (ObjectUtils.isEmpty(user)) {
            return R.ok("用户名或密码错误");
        }
        return R.ok(user);
    }

    @GetMapping("/getUserListAll")
    public R getUserListAll() {
        return R.ok(userService.getUserListAll());
    }

    @GetMapping("/getUserList")
    public R getUserList(@RequestBody UserQuery userQuery) {
        return R.ok(userService.getUserList(userQuery));
    }

    @PostMapping("/add")
    public R add(@RequestBody UserDto userDto) {
        return R.ok(userService.addUser(userDto) > 0 ? "添加成功" : "添加失败");
    }

    @PostMapping("del")
    public R del(@RequestParam("ids") Long... ids) {
        AssertUtil.isTrue(ids == null || ids.length == 0, "id参数错误");
        int row = userService.delUser(ListUtil.itemsToList(ids));
        return R.ok(row > 0 ? "删除成功" : "删除失败");
    }

    @PostMapping("/update")
    public R update(@RequestBody UserDto userDto) {
        return R.ok(userService.updateUser(userDto) > 0 ? "修改成功" : "修改失败");
    }
}
