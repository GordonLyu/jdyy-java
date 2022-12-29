package com.jdyy.controller;

import com.jdyy.commons.util.Result;
import com.jdyy.entity.Music;
import com.jdyy.entity.MusicList;
import com.jdyy.entity.vo.Page;
import com.jdyy.service.MusicListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


@Api(tags = "歌单接口")
@CrossOrigin
@RestController
@RequestMapping("/musicList")
public class MusicLIstController {


    @Resource
    MusicListService musicListService;

    //获取所有歌单
    @ApiOperation("获取所有音乐歌单")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "获取正常"),
            @ApiResponse(responseCode = "500",description = "获取异常")
    })

    @GetMapping("/getAll")
    public Result getListAll(){
        return musicListService.getListAll();
    }


    //分页获取歌单
    @ApiOperation("分页获取歌单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页数",required = true),
            @ApiImplicitParam(name = "pageSize",value = "页大小",required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "获取正常"),
            @ApiResponse(responseCode = "204",description = "没有数据或已经是尾页数据"),
            @ApiResponse(responseCode = "500",description = "获取异常")
    })
    @GetMapping("/pageList")
    public Result getUserPage(Integer currentPage,Integer pageSize){
        Page<MusicList> page = new Page<>();
        page.setPageNum(currentPage);
        page.setPageSize(pageSize);
        return musicListService.getListPage(page);
    }


    //添加歌单
    @ApiOperation("添加歌单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "listName",value = "歌单名",required = true),
            @ApiImplicitParam(paramType = "query",name = "creator",value = "创建者",required = true),
            @ApiImplicitParam(paramType = "formData",name = "coverFile",value = "封面",dataType = "File"),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "添加成功"),
            @ApiResponse(responseCode = "406",description = "文件格式错误或不支持"),
            @ApiResponse(responseCode = "500",description = "添加失败")
    })
    @PutMapping("/add")
    public Result addMusic(MusicList musicList, MultipartFile coverFile){
        System.out.println(musicList);
        if(musicList.getListName()==null||"".equals(musicList.getListName())){
            return Result.fail("音乐名不能为空");
        }else if(musicList.getCreator()==null||"".equals(musicList.getCreator())){
            return Result.fail("作者不能为空");
        }
        System.out.println(musicList);
        return musicListService.addMusicList(musicList,coverFile);
    }

    //添加一首音乐进歌单
    @ApiOperation("添加一首音乐进歌单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "listName",value = "歌单名",required = true),
            @ApiImplicitParam(paramType = "query",name = "creator",value = "创建者",required = true),
            @ApiImplicitParam(paramType = "formData",name = "coverFile",value = "封面",dataType = "File"),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "添加成功"),
            @ApiResponse(responseCode = "406",description = "文件格式错误或不支持"),
            @ApiResponse(responseCode = "500",description = "添加失败")
    })
    @PutMapping("/addMusic")
    public Result addMusicToList(Integer lid, Integer mid){
        return musicListService.addMusicToList(lid, mid);
    }



    //修改歌单--暂时不支持修改文件
    @ApiOperation("修改歌单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "listName",value = "歌单名"),
            @ApiImplicitParam(name = "creator",value = "创建者"),
            @ApiImplicitParam(name = "cover",value = "封面地址，暂时不支持修改文件"),
            @ApiImplicitParam(name = "detail",value = "简介")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "添加成功"),
            @ApiResponse(responseCode = "406",description = "文件格式错误或不支持"),
            @ApiResponse(responseCode = "500",description = "添加失败")
    })
    @PatchMapping("/modify")
    public Result modifyMusic(MusicList musicList){
        return musicListService.modifyMusicList(musicList);
    }

    //删除歌单
    @ApiOperation("删除歌单")
    @ApiImplicitParam(name = "lid",value = "歌单ID",required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "删除成功"),
            @ApiResponse(responseCode = "404",description = "未找到歌单"),
            @ApiResponse(responseCode = "500",description = "删除失败")
    })
    @DeleteMapping("/remove")
    public Result removeMusicList(Integer lid){
        return musicListService.removeMusicList(lid);
    }


    //删除一首音乐和歌单的记录
    @ApiOperation("删除一首音乐和歌单的记录")
    @ApiImplicitParam(name = "lid",value = "歌单ID",required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "删除成功"),
            @ApiResponse(responseCode = "404",description = "未找到歌单"),
            @ApiResponse(responseCode = "500",description = "删除失败")
    })
    @DeleteMapping("/removeMusic")
    public Result removeMusicList(Integer lid,Integer mid){
        return musicListService.removeMusicInList(lid, mid);
    }



    //分页利用歌单id获取当前歌单的音乐
    @ApiOperation("分页获取音乐")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页数",required = true),
            @ApiImplicitParam(name = "pageSize",value = "页大小",required = true),
            @ApiImplicitParam(name = "lid",value = "歌单id",required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "获取正常"),
            @ApiResponse(responseCode = "204",description = "没有数据或已经是尾页数据"),
            @ApiResponse(responseCode = "500",description = "获取异常")
    })
    @GetMapping("/page")
    public Result getUserPage(Integer currentPage,Integer pageSize,Integer lid){
        System.out.println(lid);
        Page<Music> page = new Page<>();
        page.setPageNum(currentPage);
        page.setPageSize(pageSize);
        return musicListService.getMusicListPage(page,lid);
    }


}
