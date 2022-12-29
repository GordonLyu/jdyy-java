package com.jdyy.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author LvXinming
 * @since 2022/11/10
 */

@ApiModel("音乐实体类")
@Data
@ToString
public class Music {
    @ApiModelProperty("音乐ID")
    private Integer musicId;//音乐ID

    @ApiModelProperty("音乐名")
    private String musicName;//音乐名

    @ApiModelProperty("音乐封面地址")
    private String coverURL;//音乐封面地址

    @ApiModelProperty("音乐静态资源地址")
    private String url;//音乐静态资源地址

    @ApiModelProperty("作者")
    private String author;//作者

    @ApiModelProperty("音乐点击数")
    private Integer clickNumber;//音乐点击数
}
