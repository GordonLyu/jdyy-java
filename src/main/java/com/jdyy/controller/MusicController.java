package com.jdyy.controller;

import com.jdyy.commons.util.Result;
import com.jdyy.entity.Music;
import com.jdyy.entity.vo.Page;
import com.jdyy.service.MusicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 音乐 控制层
 *
 * @author LvXinming
 * @since 2022/11/10
 */

@Api(tags = "音乐接口")
@CrossOrigin
@RestController
@RequestMapping("/music")
public class MusicController {

    @Resource
    MusicService musicService;


    //获取所有音乐
    @ApiOperation("获取所有音乐")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "获取正常"),
            @ApiResponse(responseCode = "500",description = "获取异常")
    })
    @GetMapping("/getAll")
    public Result getAllMusic(){
        return musicService.findAll();
    }

    //获取一首音乐
    @ApiOperation("获取一首音乐")
    @ApiImplicitParam(name = "musicId",value = "音乐ID",required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "获取正常"),
            @ApiResponse(responseCode = "500",description = "获取异常")
    })
    @GetMapping("/getOne")
    public Result getOneMusic(Integer musicId){
        return musicService.getOneMusic(musicId);
    }

    //分页获取音乐
    @ApiOperation("分页获取音乐")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页数",required = true),
            @ApiImplicitParam(name = "pageSize",value = "页大小",required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "获取正常"),
            @ApiResponse(responseCode = "204",description = "没有数据或已经是尾页数据"),
            @ApiResponse(responseCode = "500",description = "获取异常")
    })
    @GetMapping("/page")
    public Result getUserPage(Integer currentPage,Integer pageSize){
        Page<Music> page = new Page<>();
        page.setPageNum(currentPage);
        page.setPageSize(pageSize);
        return musicService.getMusicPage(page);
    }

    //添加音乐
    @ApiOperation("添加音乐")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "musicName",value = "音乐名",required = true),
            @ApiImplicitParam(paramType = "query",name = "author",value = "作者",required = true),
            @ApiImplicitParam(paramType = "formData",name = "coverFile",value = "封面",dataType = "File"),
            @ApiImplicitParam(paramType = "formData",name = "musicFile",value = "音乐文件",dataType = "File")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "添加成功"),
            @ApiResponse(responseCode = "406",description = "文件格式错误或不支持"),
            @ApiResponse(responseCode = "500",description = "添加失败")
    })
    @PutMapping("/add")
    public Result addMusic(@ApiIgnore Music music,@RequestPart(name = "coverFile",required = false) MultipartFile coverFile,@RequestPart(name = "musicFile",required = false) MultipartFile musicFile,Integer lid){
        if(music.getMusicName()==null||"".equals(music.getMusicName())){
            return Result.fail("音乐名不能为空");
        }else if(music.getAuthor()==null||"".equals(music.getAuthor())){
            return Result.fail("作者不能为空");
        }
        System.out.println(music);
        return musicService.addMusic(music,coverFile,musicFile,lid);
    }

    //修改音乐--暂时不支持修改文件
    @ApiOperation("修改音乐")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "musicName",value = "音乐名"),
            @ApiImplicitParam(name = "author",value = "作者"),
            @ApiImplicitParam(name = "coverURL",value = "封面地址，暂时不支持修改文件"),
            @ApiImplicitParam(name = "url",value = "音乐地址，暂时不支持修改文件")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "添加成功"),
            @ApiResponse(responseCode = "406",description = "文件格式错误或不支持"),
            @ApiResponse(responseCode = "500",description = "添加失败")
    })
    @PatchMapping("/modify")
    public Result modifyMusic(Music music){
        return musicService.modifyMusic(music);
    }

    //删除音乐
    @ApiOperation("删除音乐")
    @ApiImplicitParam(name = "musicId",value = "音乐ID",required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "删除成功"),
            @ApiResponse(responseCode = "404",description = "未找到音乐"),
            @ApiResponse(responseCode = "500",description = "删除失败")
    })
    @DeleteMapping("/remove")
    public Result removeMusic(Integer musicId){
        return musicService.removeMusic(musicId);
    }


    //文件上传
//    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file) throws IOException {
        return musicService.uploadFile(file);
    }

    //文件下载
    @PostMapping("/download")
    public Result download(MultipartFile file) {
        return null;
    }
}
