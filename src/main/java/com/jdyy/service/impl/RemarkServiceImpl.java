package com.jdyy.service.impl;

import com.jdyy.commons.util.Result;
import com.jdyy.entity.Remark;
import com.jdyy.entity.vo.Page;
import com.jdyy.mapper.RemarkMapper;
import com.jdyy.service.RemarkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评论 服务实现类
 *
 * @author LvXinming
 * @since 2022/11/20
 */
@Service
public class RemarkServiceImpl implements RemarkService {

    @Resource
    RemarkMapper remarkMapper;


    //获取所有评论
    @Override
    public Result getAll() {
        Result result;
        try {
            List<Remark> remarks = remarkMapper.getAll();
            result = Result.success(200,"成功获取所有评论",remarks);
        }catch (Exception e){
            e.printStackTrace();
            result = Result.fail("获取所有评论失败");
        }
        return result;
    }


    //通过音乐ID查询评论
    @Override
    public Result getRemarkByMusic(Integer mid) {
        Result result;
        try {
            List<Remark> remarks = remarkMapper.getRemarkByMusic(mid);
            result = Result.success(200,"成功获取音乐ID为 "+mid+" 的所有评论",remarks);
        }catch (Exception e){
            e.printStackTrace();
            result = Result.fail("获取评论失败");
        }
        return result;
    }

    //通过用户ID查询评论
    @Override
    public Result getRemarkByUser(Integer uid) {
        Result result;
        try {
            List<Remark> remarks = remarkMapper.getRemarkByUser(uid);
            result = Result.success(200,"成功获取用户ID为 "+uid+" 的所有评论",remarks);
        }catch (Exception e){
            e.printStackTrace();
            result = Result.fail("获取评论失败");
        }
        return result;
    }

    //通过音乐ID分页查询评论
    @Override
    public Result getRemarkPageByMusic(Integer mid, Page<Remark> page) {
        Result result;
        List<Remark> remarks;
        int dataStart = (page.getPageNum()-1)*page.getPageSize();
        int allDataSum = remarkMapper.countRemarkByMusic(mid);//所有数据数
        page.setDataStart(dataStart);
        page.setAllDataSum(allDataSum);
        try {
            remarks = remarkMapper.getRemarkPageByMusic(mid,page);
            if (remarks == null){
                result = Result.success(204,"没有数据或已经是尾页数据",null);
            }else{
                page.setPageData(remarks);
                result = Result.success("获取分页正常",page);
            }
        }catch (Exception e){
            e.printStackTrace();
            result = Result.fail("获取分页异常");
        }
        return result;
    }

    //通过用户ID分页查询评论
    @Override
    public Result getRemarkPageByUser(Integer uid, Page<Remark> page) {
        Result result;
        List<Remark> remarks;
        int dataStart = (page.getPageNum()-1)*page.getPageSize();
        int allDataSum = remarkMapper.countRemarkByUser(uid);//所有数据数
        page.setDataStart(dataStart);
        page.setAllDataSum(allDataSum);
        try {
            remarks = remarkMapper.getRemarkPageByUser(uid,page);
            if (remarks == null){
                result = Result.success(204,"没有数据或已经是尾页数据",null);
            }else{
                page.setPageData(remarks);
                result = Result.success("获取分页正常",page);
            }
        }catch (Exception e){
            e.printStackTrace();
            result = Result.fail("获取分页异常");
        }
        return result;
    }

    //添加评论
    @Override
    public Result addRemark(Remark remark) {
        Result result;
        try {
            remarkMapper.addRemark(remark);
            result = Result.success("评论成功",null);
        }catch (Exception e){
            e.printStackTrace();
            result = Result.fail("评论失败");
        }
        return result;
    }

    //删除评论
    public Result removeRemark(Integer id){
        Result result;
        try {
            Integer deleteNumber = remarkMapper.removeRemark(id);
            if(deleteNumber==0){
                return Result.fail(404,"无法删除，未找到该评论",null);
            }
            result = Result.success("评论删除成功",null);
        }catch (Exception e){
            e.printStackTrace();
            result = Result.fail("评论删除失败");
        }
        return result;
    }
}
