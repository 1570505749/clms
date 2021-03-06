package com.hnxx.zy.clms.controller;

import com.github.pagehelper.PageInfo;
import com.hnxx.zy.clms.common.utils.Page;
import com.hnxx.zy.clms.common.utils.Result;
import com.hnxx.zy.clms.core.entity.User;
import com.hnxx.zy.clms.core.entity.UserSearch;
import com.hnxx.zy.clms.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 南街北巷
 * @data 2020/5/9 9:22
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据用户userId查询组名
     *
     * @return
     */
    @PostMapping("/selectByGroup/{userId}")
    public String selectByGroupId(@PathVariable("userId") Integer userId) {
        String groupId = userService.selectByGroupId(userId);
        return groupId;
    }

    /**
     * 根据用户userId或用户名查询所有用户
     *
     * @param user
     * @return
     */
    @PostMapping("/get/byGroup")
    public Result<List<UserSearch>> getByGroup(@RequestBody User user) {
        List<UserSearch> users = userService.getByGroup(user);
        return new Result<>(users);
    }

    /**
     * 根据userId查询用户信息
     *
     * @return
     */
    @PostMapping("/get/by/{userId}")
    public Result<UserSearch> getById(@PathVariable("userId") Integer userId) {
        UserSearch user = userService.getById(userId);
        return new Result<>(user);
    }

    /**
     * 获取用户详细信息
     * 分页查询
     *
     * @param page
     * @return
     */
    @PostMapping("/get/byPage")
    public Result<PageInfo<UserSearch>> getByPage(@RequestBody Page page) {
        PageInfo<UserSearch> pages = userService.getUserByPage(page.getCurrentPage(), page.getPageSize());
        return new Result<>(pages);
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @PostMapping("/add")
    public Result<Object> insertUser(@RequestBody UserSearch user) {
        if (user.getUserName() == null || user.getUserName() == "") {
            return new Result("用户名不能为空");
        } else if (user.getUserPassword() == null || user.getUserPassword() == "") {
            return new Result("密码不能为空");
        } else {
            userService.insertUser(user);
            return new Result<>("保存成功");
        }
    }

    /**
     * 根据userId删除用户
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/delete/by/{userId}")
    public Result deleteOneById(@PathVariable("userId") Integer userId) {
        UserSearch user = userService.getById(userId);
        if (user.getIsDeleted() == 1) {
            return new Result("请勿重复删除");
        } else {
            userService.deleteOneById(userId);
            userService.updateTimeById(userId);
            return new Result("删除成功");
        }
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @PutMapping("/update/byId")
    public Result updateById(@RequestBody UserSearch user) {
        if (user.getUserName() == null || user.getUserName() == "") {
            return new Result("用户名不能为空");
        } else if (user.getUserPassword() == null || user.getUserPassword() == "") {
            return new Result("密码不能为空");
        } else {
            userService.updateById(user);
            return new Result("更新成功");
        }
    }

    /**
     * 启用
     *
     * @param userId
     * @return
     */
    @PutMapping("/status/{userId}/{isEnabled}")
    public Result updateEnable(@PathVariable("userId") Integer userId, @PathVariable("isEnabled") Integer isEnabled) {
        UserSearch enable = userService.getById(userId);
        if (isEnabled == 1 && isEnabled != enable.getIsEnabled()) {
            userService.updateEnable(userId);
            return new Result("启用成功");
        } else if (isEnabled == 1 && isEnabled == enable.getIsEnabled()) {
            return new Result("已启用");
        }

        if (isEnabled == 0 && isEnabled != enable.getIsEnabled()) {
            userService.updateDisable(userId);
            return new Result("弃用成功");
        } else {
            return new Result("已弃用");
        }
    }
}
