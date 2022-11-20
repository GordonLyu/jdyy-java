package com.jdyy.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author LvXinming
 * @since 2022/10/13
 */

@Data
@ToString
public class User {
    private int uid;//用户 ID
    private String username; //用户名
    private String password;//用户密码
    private String role;//用户角色
}
