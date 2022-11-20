package com.jdyy.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author LvXinming
 * @since 2022/11/10
 */

@Data
@ToString
public class Music {
    private int musicId;//音乐ID
    private String musicName;//音乐名
    private String coverURL;//音乐封面地址
    private String url;//音乐静态资源地址
    private String author;//作者
}
