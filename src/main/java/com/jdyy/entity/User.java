package com.jdyy.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

/**
 * @author LvXinming
 * @since 2022/10/13
 */

@Data
@ToString
public class User {
    @Schema(description = "用户ID")
    private int uid;//用户 ID

    @Schema(description = "用户名")
    private String username; //用户名

    @Schema(description = "用户密码")
    private String password;//用户密码

    @Schema(description = "用户角色")
    private String role;//用户角色
}
