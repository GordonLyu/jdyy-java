package com.jdyy.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.jdyy.commons.util.Result;
import com.jdyy.entity.Remark;
import com.jdyy.entity.vo.Page;
import com.jdyy.service.RemarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 评论 控制类
 *
 * @author LvXinming
 * @since 2022/11/20
 */

@Api(tags = "评论接口")
@CrossOrigin
@RestController
@RequestMapping("/remark")
public class RemarkController {

    @Resource
    RemarkService remarkService;

    //获取所有用户
    @ApiOperation("获取所有用户评论")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "获取正常"),
            @ApiResponse(responseCode = "500",description = "获取异常")
    })
    @SaCheckLogin
    @GetMapping("/getAll")
    public Result getAllRemark(){
        return remarkService.getAll();
    }

    //通过音乐ID查询评论
    @ApiOperation("通过音乐ID查询评论")
    @ApiImplicitParam(paramType = "query",name = "mid",value = "音乐ID",required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "获取正常"),
            @ApiResponse(responseCode = "500",description = "获取异常")
    })
    @SaCheckLogin
    @GetMapping("/getByMusic")
    public Result getRemarkByMusic(Integer mid){
        return remarkService.getRemarkByMusic(mid);
    }

    //通过用户ID查询评论
    @ApiOperation("通过用户ID查询评论")
    @ApiImplicitParam(paramType = "query",name = "uid",value = "用户ID",required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "获取正常"),
            @ApiResponse(responseCode = "500",description = "获取异常")
    })
    @SaCheckLogin
    @GetMapping("/getByUser")
    public Result getRemarkByUser(Integer uid){
        return remarkService.getRemarkByUser(uid);
    }

    //通过音乐ID分页查询评论
    @ApiOperation("通过音乐ID分页查询评论")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "mid",value = "音乐ID",required = true),
            @ApiImplicitParam(paramType = "query",name = "currentPage",value = "当前页数",required = true),
            @ApiImplicitParam(paramType = "query",name = "pageSize",value = "页大小",required = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "获取正常"),
            @ApiResponse(responseCode = "204",description = "没有数据或已经是尾页数据"),
            @ApiResponse(responseCode = "500",description = "获取异常")
    })
    @SaCheckLogin
    @GetMapping("/pageByMusic")
    public Result getRemarkPageByMusic(Integer mid,Integer currentPage,Integer pageSize){
        Page<Remark> page = new Page<>();
        page.setPageNum(currentPage);
        page.setPageSize(pageSize);
        return remarkService.getRemarkPageByMusic(mid,page);
    }

    //通过用户ID分页查询评论
    @ApiOperation("通过用户ID分页查询评论")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid",value = "用户ID",required = true),
            @ApiImplicitParam(paramType = "query",name = "currentPage",value = "当前页数",required = true),
            @ApiImplicitParam(paramType = "query",name = "pageSize",value = "页大小",required = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "获取正常"),
            @ApiResponse(responseCode = "204",description = "没有数据或已经是尾页数据"),
            @ApiResponse(responseCode = "500",description = "获取异常")
    })
    @SaCheckLogin
    @GetMapping("/pageByUser")
    public Result getRemarkPageByUser(Integer uid,Integer currentPage,Integer pageSize){
        Page<Remark> page = new Page<>();
        page.setPageNum(currentPage);
        page.setPageSize(pageSize);
        return remarkService.getRemarkPageByUser(uid,page);
    }

    //添加评论
    @ApiOperation("添加评论")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "mid",value = "音乐ID",required = true),
            @ApiImplicitParam(paramType = "query",name = "uid",value = "用户ID",required = true),
            @ApiImplicitParam(paramType = "query",name = "content",value = "评论内容",required = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "添加成功"),
            @ApiResponse(responseCode = "406",description = "评论内容为空或字数不够"),
            @ApiResponse(responseCode = "500",description = "添加失败")
    })
    @SaCheckLogin
    @PutMapping("/add")
    public Result addRemark(@RequestBody Remark remark){
        if(remark.getContent()==null||"".equals(remark.getContent())){
            return Result.fail(406,"评论不能为空");
        }else if(remark.getContent().length()<=6){
            return Result.fail(406,"评论字数不少于6位");
        }
        return remarkService.addRemark(remark);
    }

    //删除评论
    @ApiOperation("删除评论")
    @ApiImplicitParam(paramType = "query",name = "id",value = "评论ID",required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "删除成功"),
            @ApiResponse(responseCode = "404",description = "未找到评论"),
            @ApiResponse(responseCode = "500",description = "删除失败")
    })
    @SaCheckLogin
    @DeleteMapping("/remove")
    public Result removeRemark(Integer id){
        return remarkService.removeRemark(id);
    }
}