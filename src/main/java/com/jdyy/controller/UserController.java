package com.jdyy.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.jdyy.commons.util.Result;
import com.jdyy.entity.User;
import com.jdyy.entity.vo.Page;
import com.jdyy.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户控制层
 *
 * @author LvXinming
 * @since 2022/10/13
 */

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    //分页查询用户
    @SaCheckLogin
    @GetMapping("/getPage")
    public Result getUserPage(Page<User> page){
        return userService.getUserPage(page);
    }

    //获取所有用户
    @SaCheckLogin
    @GetMapping("/getAll")
    public Result getAllUsers(){
        return userService.findAll();
    }

    //添加用户
    @SaCheckLogin
    @PostMapping("/add")
    public Result addUser(@RequestBody User user){
        if(user.getPassword().length()<6|user.getPassword().length()>20){
            return new Result(500,"密码长度必须在6-20之间");
        }
        return userService.addUser(user);
    }

    //删除用户
    @SaCheckLogin
    @PostMapping("/remove")
    public Result removeUser(Integer uid){
//        System.out.println(user);
        System.out.println("ID为"+uid);
        User user = new User();
        user.setUid(uid);
        if (uid==0){
            return Result.fail("你无法删除超级管理员");
        }

        return userService.removeUser(user);
    }

    //用户登录
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        return userService.login(user);
    }

    //用户注册
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        if(user.getUsername()==null){
            return new Result(500,"用户名不能为空");
        }else if(user.getUsername().length()<5||user.getUsername().length()>20){
            return new Result(500,"用户名长度必须在5-16之间");
        }
        if(user.getPassword()==null){
            return new Result(500,"密码不能为空");
        }else if(user.getPassword().length()<6||user.getPassword().length()>20){
            return new Result(500,"密码长度必须在6-20之间");
        }
        return userService.register(user);
    }
}
