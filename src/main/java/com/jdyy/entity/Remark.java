package com.jdyy.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

/**
 * 评论实体类
 *
 * @author LvXinming
 * @since 2022/11/20
 */

@Data
@ToString
public class Remark {
    @Schema(description = "评论ID")
    private Integer id;

    @Schema(description = "音乐ID")
    private Integer mid;

    @Schema(description = "用户ID")
    private Integer uid;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "评论时间")
    private String remarkTime;

    @Schema(description = "评论点赞数")
    private Integer goodNum;
}
