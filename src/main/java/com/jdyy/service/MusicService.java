package com.jdyy.service;

import com.jdyy.commons.util.Result;
import com.jdyy.entity.Music;
import com.jdyy.entity.vo.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author LvXinming
 * @since 2022/11/11
 */
public interface MusicService {

    //分页查询
    Result getMusicPage(Page<Music> page);

    //获取所有音乐
    Result findAll();

    //获取一首音乐
    Result getOneMusic(Integer id);

    //添加音乐
    Result addMusic(Music music, MultipartFile cover, MultipartFile musicFile,Integer lid);

    //修改一首音乐
    Result modifyMusic(Music music);

    //删除音乐
    Result removeMusic(Integer mid);


    //文件上传
    Result uploadFile(MultipartFile file) throws IOException;
}
