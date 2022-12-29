package com.jdyy.service.impl;

import com.jdyy.commons.util.Result;
import com.jdyy.controller.MusicController;
import com.jdyy.entity.Music;
import com.jdyy.entity.MusicList;
import com.jdyy.entity.vo.Page;
import com.jdyy.mapper.MusicListMapper;
import com.jdyy.service.MusicListService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class MusicListServiceImpI implements MusicListService {

    @Resource
    private MusicListMapper musicListMapper;


    //分页查询歌单
    @Override
    public Result getListPage(Page<MusicList> page) {
        Result result;
        List<MusicList> musicList;
        //从第几条开始，-1是因为页数是从1开始，而查询的数据是从0开始
        int dataStart = (page.getPageNum()-1)*page.getPageSize();
        int allDataSum = musicListMapper.countMusic();//所有数据数
        System.out.println("当前页数："+page.getPageNum()+"，当前一页的大小："+page.getPageSize());
        System.out.println("从第 "+dataStart+" 条开始");
        page.setDataStart(dataStart);
        page.setAllDataSum(allDataSum);
        try {
            musicList = musicListMapper.getListPage(page);
            if (musicList == null){
                result = Result.success(204,"没有数据或已经是尾页数据",null);
            }else{
                page.setPageData(musicList);
                result = Result.success("获取分页正常",page);
            }
        }catch (Exception e){
            e.printStackTrace();//打印错误信息
            result = Result.fail("获取分页异常");
        }
        return result;
    }

    //分页查询
    @Override
    public Result getMusicListPage(Page<Music> page,Integer lid) {
        Result result;
        List<Music> music;
        //从第几条开始，-1是因为页数是从1开始，而查询的数据是从0开始
        int dataStart = (page.getPageNum()-1)*page.getPageSize();
        int allDataSum = musicListMapper.countListMusic(lid);//当前歌单所有数据数
        System.out.println("当前页数："+page.getPageNum()+"，当前一页的大小："+page.getPageSize());
        System.out.println("从第 "+dataStart+" 条开始");
        page.setDataStart(dataStart);
        page.setAllDataSum(allDataSum);
        try {
            music = musicListMapper.getMusicListPage(page,lid);
            System.out.println(music);
            if (music == null){
                result = Result.success(204,"没有数据或已经是尾页数据",null);
            }else{
                page.setPageData(music);
                result = Result.success("获取分页正常",page);
            }
        }catch (Exception e){
            e.printStackTrace();//打印错误信息
            result = Result.fail("获取分页异常");
        }
        return result;
    }

    //获取所有音乐歌单
    @Override
    public Result getListAll() {
        Result result;
        try {
            List<MusicList> musics = musicListMapper.getListAll();
            result = Result.success("成功获取所有音乐",musics);
        }catch (Exception e){
            e.printStackTrace();
            Map<String, Object> map = new HashMap<>();
            map.put("errMsg",e.getMessage());
            result = Result.fail("获取所有音乐失败",map);
        }
        return result;
    }


    //根据歌单id获取一条歌单
    @Override
    public Result getOneMusicList(Integer lid) {
        Result result;
        try {
            MusicList oneMusicList = musicListMapper.getOneMusicList(lid);
            if (oneMusicList==null){
                return Result.fail(504,"找不到此音乐",null);
            }
            result = Result.success("成功获取音乐",oneMusicList);
        }catch (Exception e){
            e.printStackTrace();
            result = Result.fail("获取音乐失败",null);
        }
        return result;
    }


    //删除歌单
    @Override
    public Result removeMusicList(Integer lid) {
        Result result;
        MusicList oneMusicList = musicListMapper.getOneMusicList(lid);
        try {
            if(oneMusicList==null)
                return new Result(404,"删除失败，未找到此歌单");
            musicListMapper.removeMusicList(lid);
            musicListMapper.removeListRecord(lid);

            //删除文件
            String relativePath = MusicController.class.getClassLoader().getResource("").getPath();//获取绝对路径
            relativePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);//处理字符问题

            String coverURL = relativePath+"static/"+oneMusicList.getCover();//获取音乐封面图片地址

            File coverImage = new File(coverURL);//获取音乐封面图片名
            String fileDeleteMsg = "文件删除情况：";
            if(coverImage.delete()){
                fileDeleteMsg += "，歌单封面删除成功";
                System.out.println("歌单封面删除成功");
            }else{
                fileDeleteMsg += "，歌单封面不存在";
                System.out.println("歌单封面不存在");
            }

            Map<String,Object> map = new HashMap<>();
            map.put("fileDeleteMsg",fileDeleteMsg);
            map.put("musicDeleteMsg",oneMusicList);
            result = Result.success(200,"歌单删除成功",map);
        }catch (Exception e){
            e.printStackTrace();
            Map<String, Object> map = new HashMap<>();
            map.put("errMsg",e.getMessage());
            result = Result.fail("歌单删除失败",map);
        }
        return result;
    }



    //删除一首音乐和歌单的记录
    @Override
    public Result removeMusicInList(Integer lid, Integer mid) {
        Result result;
        try {
            musicListMapper.removeMusicInList(lid, mid);
            result = Result.success("歌单中成功删除音乐",null);
        }catch (Exception e){
            e.printStackTrace();
            result = Result.fail("歌单中删除音乐失败",null);
        }
        return result;
    }



    //修改一条歌单
    public Result modifyMusicList(MusicList musicList){
        Result result;
        try {
            System.out.println("@"+musicList);
            musicListMapper.modifyMusicList(musicList);
            result = Result.success("修改音乐成功");
        }catch (Exception e){
            e.printStackTrace();
            result = Result.fail("修改音乐失败");
        }
        return result;
    }



    //添加一首音乐进歌单
    @Override
    public Result addMusicToList(Integer lid, Integer mid) {
        Result result;
        try {
            musicListMapper.addMusicToList(lid, mid);
            result = Result.success("已添加音乐到歌单",null);
        }catch (Exception e){
            e.printStackTrace();
            result = Result.fail("添加失败");
        }
        return result;
    }

    //添加歌单
    @Override
    public Result addMusicList(MusicList musicList, MultipartFile coverFile){
        Result result;
        try {
            musicListMapper.addMusicList(musicList);//插入后获取歌单id到musicList实体类中
            musicList.setLid(musicListMapper.getLid());
            System.out.println("#"+(musicListMapper.getLid()));
            if (coverFile!=null){
                Result uploadResult = upload(coverFile,musicList,1);
                if(uploadResult.getCode()!=200){
                    removeMusicList(musicList.getLid());
                    musicListMapper.modifyAutoincrement(musicListMapper.getLid()-1);
                    return uploadResult;
                }
                String cover = (String) uploadResult.getData();
                musicList.setCover(cover);

            }
//            musicList.setCreateTime(new Date().toString());
            modifyMusicList(musicList);//将文件地址存入到数据库
//            System.out.println(musicList);
            Map<String,Object> map = new HashMap<>();
            map.put("musicAddMsg",musicList);//返回前端歌曲信息
            result = Result.success(200,"添加成功",map);
        }catch (Exception e){
            e.printStackTrace();
            result = Result.fail("添加失败",null);
        }
        return result;
    }






    //音乐文件和音乐封面图片上传
    public Result upload(MultipartFile file,MusicList musicList,int code) {//code 0为自判定 1为图片 2为音频
        Result result;
        String originFileName = file.getOriginalFilename();//原始文件名
        if (file.isEmpty()){
            return Result.fail("文件不存在");
        }else if(originFileName==null){
            return Result.fail("文件名不能为空");
        }

        //路径处理
        String relativePath = MusicController.class.getClassLoader().getResource("").getPath();//获取绝对路径
        relativePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);//处理字符问题
        System.out.println(relativePath);
        String savePath = "static/musicList/";//保存路径
        String path = relativePath+savePath;//拼接

        //文件后缀处理
        String fileSuffix = originFileName.substring(originFileName.lastIndexOf('.'));//文件后缀
        String[] supportImgSuffix = {".jpg",".png"};//支持的音乐封面图片后缀
        String[] supportAudioSuffix = {".mp3"};//支持的音频后缀
        //判断文件后缀

        //code为1，应为图片时; code为2，应为音频时
        if(code==1&&!Arrays.stream(supportImgSuffix).toList().contains(fileSuffix)){
            return Result.fail(406,"文件上传错误，文件类型应为图片",null);
        }else if(code==2&&!Arrays.stream(supportAudioSuffix).toList().contains(fileSuffix)){
            return Result.fail(406,"文件上传错误，文件类型应为音频",null);
        }

        boolean isImg = false;//是否为图片
        if (Arrays.stream(supportImgSuffix).toList().contains(fileSuffix)){
            isImg = true;
            path+="cover";
        }else if(Arrays.stream(supportAudioSuffix).toList().contains(fileSuffix)){
            path+="audio";
        }else{
            return Result.fail(406,"不支持此文件",null);
        }

        //保存文件
        File realPath = new File(path);//创建文件夹
        if(!realPath.exists()){//文件夹不存在则创建文件夹
            realPath.mkdirs();
        }

        try {

            //改名
            String newFileName = originFileName;
            if (musicList!=null){
                newFileName = "lid_"+musicList.getLid()+fileSuffix;
            }

            file.transferTo(new File(path,newFileName));

            result = Result.success(200,"文件上传成功","musicList/"+(isImg?"cover/":"audio/")+newFileName);
        } catch (IOException e) {
            e.printStackTrace();
            result = Result.fail("文件上传失败",e.getMessage());
        }
        return result;
    }

}
