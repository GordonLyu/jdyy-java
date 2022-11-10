package com.jdyy.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.jdyy.commons.util.Result;
import com.jdyy.commons.util.Status;
import com.jdyy.entity.User;
import com.jdyy.entity.vo.Page;
import com.jdyy.mapper.UserMapper;
import com.jdyy.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
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
                result = Result.success(201,"没有数据或已经是尾页数据",null);
            }else{
                page.setPageData(users);
                result = Result.success("获取分页正常",page);
            }
        }catch (Exception e){
            e.printStackTrace();
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
            result = Result.success(users);
        }catch (Exception e){
            result = Result.fail(null);
        }
        return result;
    }

    //添加用户
    @Override
    public Result addUser(User user) {
        Result result;
        try {
            userMapper.addUser(user);
            result = new Result(200,"用户添加成功");
        }catch (Exception e){
            result = new Result(500,"用户添加失败");
            userMapper.fixAutoincrement();
        }
        return result;
    }

    //删除用户
    @Override
    public Result removeUser(User user) {
        Result result;
        User aUser = userMapper.getUser(user);
        try {
            if(aUser==null)
                return new Result(500,"删除失败，未找到此用户");
            userMapper.removeUser(user);
            result = new Result(200,"用户删除成功");
            //删除后ID号自增是否向前呢？这是个问题
            userMapper.fixAutoincrement();
        }catch (Exception e){
            result = new Result(500,"用户删除失败");
        }
        return result;
    }

    //登录
    @Override
    public Result login(User userLogin) {
        Result result = null;
        try {
            User user = userMapper.login(userLogin);
            if (user!=null){
                StpUtil.login(user.getUid());
                Map<String, Object> map = new HashMap<>();
                map.put("user",user);//添加用户信息到data
                map.put("token",StpUtil.getTokenInfo());//添加token到data
                result = new Result(Status.SUCCESS,map);
            }
        }catch (Exception e){
            result = new Result(Status.UNKNOWN_FAIL);
        }
        return result;
    }

    //注册
    public Result register(User user) {
        Result result;
        try {
            userMapper.addUser(user);
            result =  Result.success("注册成功",null);

        }catch (DuplicateKeyException e){
            result = Result.fail("注册失败，用户已存在");
            userMapper.fixAutoincrement();
        }catch (Exception e){
            e.printStackTrace();
            result = Result.fail("注册失败");
            userMapper.fixAutoincrement();
        }
        return result;
    }
}
