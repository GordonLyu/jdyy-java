package com.jdyy.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@ApiModel("歌单实体类")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MusicList {
    private Integer lid;
    private String listName;
    private String cover;
    private String creator;
    private String createTime;
    private String detail;
}
