package com.jdyy.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * 分页实体类
 *
 * @author LvXinming
 * @since 2022/10/30
 */

@Data
public class Page<T> {
    int pageNum;//当前页数
    int dataStart;//数据开始
    int pageSize;//一页有多少条数据
    int allDataSum;//所有数据总量
    private List<T> pageData;//一页的数据
}