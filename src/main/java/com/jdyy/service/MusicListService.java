package com.jdyy.service;

import com.jdyy.commons.util.Result;
import com.jdyy.entity.Music;
import com.jdyy.entity.MusicList;
import com.jdyy.entity.vo.Page;
import org.springframework.web.multipart.MultipartFile;

public interface MusicListService{

    //分页查询歌单
    Result getListPage(Page<MusicList> page);

    //分页查询
    Result getMusicListPage(Page<Music> page,Integer lid);

    //获取所有歌单
    Result getListAll();

    //添加音乐
    Result addMusicList(MusicList musicList, MultipartFile coverFile);

    //添加一首音乐进歌单
    Result addMusicToList(Integer lid, Integer mid);


    //获取单条歌单
    Result getOneMusicList(Integer lid);

    //删除音乐
    Result removeMusicList(Integer lid);

    //删除一首音乐和歌单的记录
    Result removeMusicInList(Integer lid, Integer mid);

    //修改一个歌单
    Result modifyMusicList(MusicList musicList);
}
