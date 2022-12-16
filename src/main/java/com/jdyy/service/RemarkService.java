package com.jdyy.service;

import com.jdyy.commons.util.Result;
import com.jdyy.entity.Remark;
import com.jdyy.entity.vo.Page;

/**
 * 评论 服务接口
 *
 * @author LvXinming
 * @since 2022/11/20
 */
public interface RemarkService {

    //获取所有评论
    Result getAll();

    //通过音乐ID查询评论
    Result getRemarkByMusic(Integer mid);

    //通过用户ID查询评论
    Result getRemarkByUser(Integer uid);

    //通过音乐ID分页查询评论
    Result  getRemarkPageByMusic(Integer mid, Page<Remark> page);

    //通过用户ID分页查询评论
    Result  getRemarkPageByUser(Integer uid, Page<Remark> page);

    //添加评论
    Result addRemark(Remark remark);

    //删除评论
    Result removeRemark(Integer id);
}
