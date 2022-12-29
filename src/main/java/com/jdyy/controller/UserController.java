package com.jdyy.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.jdyy.commons.util.Result;
import com.jdyy.entity.User;
import com.jdyy.entity.vo.Page;
import com.jdyy.service.UserService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户控制层
 *
 * @author LvXinming
 * @since 2022/10/13
 */

@Api(tags = "用户接口")
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    //分页查询用户
    @ApiOperation("分页查询用户数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页数",required = true),
            @ApiImplicitParam(name = "pageSize",value = "页大小",required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "获取正常"),
            @ApiResponse(responseCode = "204",description = "没有数据或已经是尾页数据"),
            @ApiResponse(responseCode = "500",description = "获取异常")
    })
    @SaCheckLogin
    @GetMapping("/page")
    public Result getUserPage(Integer currentPage,Integer pageSize){
        Page<User> page = new Page<>();
        System.out.println(currentPage+" "+pageSize);
        page.setPageNum(currentPage);
        page.setPageSize(pageSize);
        return userService.getUserPage(page);
    }

    //获取所有用户
    @ApiOperation("获取所有用户")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "获取正常"),
            @ApiResponse(responseCode = "500",description = "获取异常")
    })
    @SaCheckLogin
    @GetMapping("/getAll")
    public Result getAllUsers(){
        return userService.findAll();
    }

    //根据Id获取所有用户
    @ApiOperation("根据Id获取所有用户")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "获取正常"),
            @ApiResponse(responseCode = "500",description = "获取异常")
    })
    @SaCheckLogin
    @GetMapping("/getUserById")
    public Result getUserById(Integer uid){
        return userService.getUserById(uid);
    }

    //添加用户
    @ApiOperation("添加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名",required = true),
            @ApiImplicitParam(name = "password",value = "密码",required = true),
            @ApiImplicitParam(name = "role",value = "角色")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "添加成功"),
            @ApiResponse(responseCode = "406",description = "用户名或密码格式错误"),
            @ApiResponse(responseCode = "409",description = "用户已存在"),
            @ApiResponse(responseCode = "500",description = "添加失败")
    })
    @SaCheckLogin
    @PutMapping("/add")
    public Result addUser(@RequestBody User user){
        if(user.getUsername()==null||"".equals(user.getUsername())){
            return new Result(500,"用户名不能为空");
        }else if(user.getUsername().length()<5||user.getUsername().length()>20){
            return new Result(500,"用户名长度必须在5-16之间");
        }
        if(user.getPassword()==null||"".equals(user.getPassword())){
            return new Result(500,"密码不能为空");
        }else if(user.getPassword().length()<6||user.getPassword().length()>20){
            return new Result(500,"密码长度必须在6-20之间");
        }

        //role为空时，则默认为user
        if ("".equals(user.getRole())||user.getRole()==null){
            user.setRole("user");
        }
        return userService.addUser(user);
    }

    //删除用户
    @ApiOperation("删除用户")
    @ApiImplicitParam(name = "uid",value = "用户ID",required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "删除成功"),
            @ApiResponse(responseCode = "404",description = "用户不存在"),
            @ApiResponse(responseCode = "500",description = "删除失败")
    })
    @SaCheckLogin
    @DeleteMapping("/remove")
    public Result removeUser(Integer uid){
        User user = new User();
        user.setUid(uid);
        if (uid==0){
            return Result.fail("你无法删除超级管理员");
        }
        return userService.removeUser(user);
    }

    //用户登录
    @ApiOperation("用户登录")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "登陆成功"),
            @ApiResponse(responseCode = "401",description = "用户名或密码错误"),
            @ApiResponse(responseCode = "500",description = "登录失败")
    })
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        return userService.login(user);
    }

    //用户注册
    @ApiOperation("用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名",required = true,dataType = "String"),
            @ApiImplicitParam(name = "password",value = "密码",required = true,dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "注册成功"),
            @ApiResponse(responseCode = "406",description = "用户名或密码格式错误等"),
            @ApiResponse(responseCode = "409",description = "用户已存在"),
            @ApiResponse(responseCode = "500",description = "注册失败")
    })
    @PutMapping("/register")
    public Result register(@RequestBody User user){
        if(user.getUsername()==null||"".equals(user.getUsername())){
            return new Result(406,"用户名不能为空");
        }else if(user.getUsername().length()<5||user.getUsername().length()>20){
            return new Result(406,"用户名长度必须在5-16之间");
        }
        if(user.getPassword()==null||"".equals(user.getPassword())){
            return new Result(406,"密码不能为空");
        }else if(user.getPassword().length()<6||user.getPassword().length()>20){
            return new Result(406,"密码长度必须在6-20之间");
        }
        user.setRole("user");
        return userService.register(user);
    }
}
