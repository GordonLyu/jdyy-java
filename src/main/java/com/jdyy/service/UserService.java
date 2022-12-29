package com.jdyy.service;

import com.jdyy.commons.util.Result;
import com.jdyy.entity.User;
import com.jdyy.entity.vo.Page;

/**
 * @author LvXinming
 * @since 2022/10/13
 */


public interface UserService {

    //分页查询
    Result getUserPage(Page<User> page);

    //查所有
    Result findAll();

    //添加用户
    Result addUser(User user);

    //删除用户
    Result removeUser(User user);

    //登录
    Result login(User userLogin);

    //注册
    Result register(User userLogin);

    //根据Id获取所有用户
    Result getUserById(Integer uid);
}