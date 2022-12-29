package com.jdyy.mapper;

import com.jdyy.entity.Remark;
import com.jdyy.entity.vo.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author LvXinming
 * @since 2022/11/20
 */

@Mapper
public interface RemarkMapper {

    //获取所有评论
    @Select("select * from remark")
    List<Remark> getAll();

    //通过音乐ID查询评论数量
    @Select("select count(id) from remark where mid = #{mid}")
    Integer countRemarkByMusic(Integer mid);

    //通过用户ID查询评论数量
    @Select("select count(id) from remark where uid = #{uid}")
    Integer countRemarkByUser(Integer uid);

    //通过音乐ID查询评论
    @Select("select * from remark where mid = #{mid} order by remark_time desc")
    List<Remark> getRemarkByMusic(Integer mid);

    //通过用户ID查询评论
    @Select("select * from remark where uid = #{uid}")
    List<Remark> getRemarkByUser(Integer uid);

    //通过音乐ID分页查询评论
    @Select("select * from remark where mid = #{mid} limit #{page.dataStart},#{page.pageSize};")
    List<Remark> getRemarkPageByMusic(Integer mid, Page<Remark> page);

    //通过用户ID分页查询评论
    @Select("select * from remark where uid = #{uid} limit #{page.dataStart},#{page.pageSize};")
    List<Remark> getRemarkPageByUser(Integer uid, Page<Remark> page);

    //添加评论
    @Insert("insert into remark (mid, uid,username, content) values(#{mid},#{uid},#{username},#{content})")
    void addRemark(Remark remark);

    //删除评论
    @Insert("delete from remark where id = #{id}")
    Integer removeRemark(Integer id);

    /**
     * 为了解决添加失败后自增不连续的问题
     */
    @Update("alter table remark AUTO_INCREMENT=1;")
    void fixAutoincrement();
}