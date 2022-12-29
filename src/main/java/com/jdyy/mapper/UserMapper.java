package com.jdyy.mapper;

import com.jdyy.commons.util.Result;
import com.jdyy.entity.User;
import com.jdyy.entity.vo.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author LvXinming
 * @since 2022/10/13
 */

@Mapper
public interface UserMapper {

    //分页查询
    List<User> getUserPage(Page<User> page);

    //查用户总数
    int countUser();

    //查询所有
    List<User> getAll();


    //根据Id获取所有用户
    @Select("select * from user where uid = #{uid};")
    List<User> getUserById(Integer uid);

    //查一个用户
    User getOneUser(User user);

    //添加用户
    void addUser(User user);

    //删除用户
    void removeUser(User user);

    //登录
    User login(User user);

    /**
     * 为了解决添加失败后自增不连续的问题
     */
    @Update("alter table user AUTO_INCREMENT=1;")
    void fixAutoincrement();
}