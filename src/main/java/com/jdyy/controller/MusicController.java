package com.jdyy.controller;

import com.jdyy.commons.util.Result;
import com.jdyy.entity.Music;
import com.jdyy.entity.vo.Page;
import com.jdyy.service.MusicService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 音乐 控制层
 *
 * @author LvXinming
 * @since 2022/11/10
 */
@CrossOrigin
@RestController
@RequestMapping("/music")
public class MusicController {

    @Resource
    MusicService musicService;

    //获取所有音乐
    @GetMapping("/getAll")
    public Result getAllMusic(){
        return musicService.findAll();
    }

    //获取一首音乐
    @GetMapping("/getOne")
    public Result getOneMusic(Music music){
        return musicService.getOneMusic(music);
    }

    //分页获取音乐
    @GetMapping("/getPage")
    public Result getUserPage(Page<Music> page){
        return musicService.getMusicPage(page);
    }

    //添加音乐
    @PostMapping("/add")
    public Result addMusic(Music music, MultipartFile coverFile, MultipartFile musicFile){
        return musicService.addMusic(music,coverFile,musicFile);
    }

    //修改音乐
    @PostMapping("/modify")
    public Result modifyMusic(Music music){
        return musicService.modifyMusic(music);
    }

    //删除音乐
    @PostMapping("/remove")
    public Result removeMusic(Music music){
        return musicService.removeMusic(music);
    }


    //文件上传
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
