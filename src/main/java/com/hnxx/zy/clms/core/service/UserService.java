package com.hnxx.zy.clms.core.service;

import com.github.pagehelper.PageInfo;
import com.hnxx.zy.clms.common.utils.Page;
import com.hnxx.zy.clms.core.entity.ReportStatistics;
import com.hnxx.zy.clms.core.entity.User;
import com.hnxx.zy.clms.core.entity.UserSearch;

import java.util.List;

/**
 * @author 南街北巷
 * @data 2020/5/9 9:25
 */
public interface UserService {
    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<User> getByPage(Page<User> page);

    /**
     * 根据id查询用户信息
     * 包括弃用的和删除的
     *
     * @param id
     * @return
     */
    UserSearch getById(Integer id);

    /**
     * 根据用户id删除用户(将删除标识位置为1)
     *
     * @param id
     */
    void deleteOneById(Integer id);

    /**
     * 更新用户操作时间
     *
     * @param id
     */
    void updateTimeById(Integer id);

    /**
     * 根据用户id查询组Id
     *
     * @param id
     * @return
     */
    String selectByGroupId(Integer id);

    /**
     * 根据用户id或用户名获取用户详细信息
     *
     * @param user
     * @return
     */
    List<UserSearch> getByGroup(User user);

    /**
     * 获取用户详细信息(分页)
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo getUserByPage(Integer pageNum, Integer pageSize);

    /**
     * 打印测试用户信息
     *
     * @param username
     * @return
     */
    User selectByName(String username);

    /**
     * 打印测试用户信息
     *
     * @param userId
     * @return
     */
    User selectById(Integer userId);

    /**
     * 获取登录用户名
     *
     * @return
     */
    String getUserName();

    /**
     * 获取登录用户名
     *
     * @param name
     * @return
     */
    Integer selectUserId(String name);

    /**
     * 获取班级、组人数
     *
     * @param page
     * @return
     */
    int getUserNum(Page<ReportStatistics> page);

    /**
     * 获取班级各组组名
     *
     * @param page
     * @return
     */
    Integer[] getGroupIds(Page<ReportStatistics> page);

    /**
     * 用户更新头像
     *
     * @param userId
     * @param userIcon
     */
    void updateUserIconById(Integer userId, String userIcon);

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    void insertUser(UserSearch user);


    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    void updateById(UserSearch user);

    /**
     * 启用
     *
     * @param userId
     */
    void updateEnable(Integer userId);

    /**
     * 弃用
     *
     * @param userId
     */
    void updateDisable(Integer userId);

    /**
     * 前台用户注册
     *
     * @param user
     * @return
     */
    Integer addUser(User user);
}
