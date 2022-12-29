package com.jdyy.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.jdyy.commons.util.Result;
import com.jdyy.entity.User;
import com.jdyy.entity.vo.Page;
import com.jdyy.mapper.UserMapper;
import com.jdyy.service.UserService;
import io.swagger.models.auth.In;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 用户 服务实现类
 *
 * @author LvXinming
 * @since 2022/10/13
 */

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    //分页查询
    public Result getUserPage(Page<User> page){
        Result result;
        List<User> users;
        //从第几条开始，-1是因为页数是从1开始，而查询的数据是从0开始
        int dataStart = (page.getPageNum()-1)*page.getPageSize();
        int allDataSum = userMapper.countUser();//所有数据
        System.out.println("当前页数"+page.getPageNum()+" "+"当前一页的大小"+page.getPageSize());
        System.out.println("从第 "+dataStart+" 条开始");
        page.setDataStart(dataStart);
        page.setAllDataSum(allDataSum);
        try {
            users = userMapper.getUserPage(page);
            if (users == null){
                result = Result.success(204,"没有数据或已经是尾页数据",null);
            }else{
                page.setPageData(users);
                result = Result.success("获取分页正常",page);
            }
        }catch (Exception e){
            e.printStackTrace();//打印错误信息
            result = Result.fail("获取分页异常");
        }
        return result;
    }

    //查询所有用户
    @Override
    public Result findAll() {
        Result result;
        try {
            List<User> users = userMapper.getAll();
            result = Result.success(200,"成功获取所有用户",users);
        }catch (Exception e){
            e.printStackTrace();
            result = Result.fail("获取所有用户失败");
        }
        return result;
    }
    //根据Id获取所有用户
    @Override
    public Result getUserById(Integer uid) {
        Result result;
        try {
            List<User> users = userMapper.getUserById(uid);
            result = Result.success(200,"成功获取uid为"+uid+"的用户",users);
        }catch (Exception e){
            e.printStackTrace();
            result = Result.fail("获取用户失败");
        }
        return result;
    }

    //添加用户
    @Override
    public Result addUser(User user) {
        Result result;
        try {
            userMapper.addUser(user);
            result =  Result.success("添加成功");

        }catch (DuplicateKeyException e){
            result = Result.fail(406,"添加失败，用户已存在");
            userMapper.fixAutoincrement();
        }catch (Exception e){
            e.printStackTrace();
            result = Result.fail("添加失败");
            userMapper.fixAutoincrement();
        }
        return result;
    }

    //删除用户
    @Override
    public Result removeUser(User user) {
        Result result;
        User aUser = userMapper.getOneUser(user);
        try {
            if(aUser==null)
                return new Result(404,"删除失败，未找到此用户");
            userMapper.removeUser(user);
            result = Result.success("删除成功",null);
            //删除后ID号自增是否向前呢？这是个问题
//            userMapper.fixAutoincrement();
        }catch (Exception e){
            e.printStackTrace();
            result = new Result(500,"删除失败");
        }
        return result;
    }

    //登录
    @Override
    public Result login(@RequestBody User userLogin) {
        Result result;
        try {
            User user = userMapper.login(userLogin);
            if (user!=null){
                StpUtil.login(user.getUid());
                Map<String, Object> map = new HashMap<>();
                map.put("user",user);//添加用户信息到data
                map.put("token",StpUtil.getTokenInfo());//添加token到data
//                System.out.println(map.get("token"));
                result = new Result(200,"登录成功",map);
            }else {
                result = Result.fail(401,"用户名或密码错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            result = Result.fail("登录失败");
        }
        return result;
    }

    //注册
    public Result register(@RequestBody User user) {
        Result result;
        try {
            userMapper.addUser(user);
            result =  Result.success("注册成功");

        }catch (DuplicateKeyException e){
            result = Result.fail(409,"注册失败，用户已存在");
            userMapper.fixAutoincrement();
        }catch (Exception e){
            e.printStackTrace();
            result = Result.fail("注册失败");
            userMapper.fixAutoincrement();
        }
        return result;
    }
}
