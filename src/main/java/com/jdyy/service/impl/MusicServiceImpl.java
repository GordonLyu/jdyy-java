package com.jdyy.service.impl;

import com.jdyy.commons.util.Result;
import com.jdyy.controller.MusicController;
import com.jdyy.entity.Music;
import com.jdyy.entity.vo.Page;
import com.jdyy.mapper.MusicMapper;
import com.jdyy.service.MusicService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 音乐 服务实现类
 *
 * @author LvXinming
 * @since 2022/11/11
 */
@Service
public class MusicServiceImpl implements MusicService {

    @Resource
    MusicMapper musicMapper;

    //分页查询
    @Override
    public Result getMusicPage(Page<Music> page) {
        Result result;
        List<Music> music;
        //从第几条开始，-1是因为页数是从1开始，而查询的数据是从0开始
        int dataStart = (page.getPageNum()-1)*page.getPageSize();
        int allDataSum = musicMapper.countMusic();//所有数据数
        System.out.println("当前页数："+page.getPageNum()+"，当前一页的大小："+page.getPageSize());
        System.out.println("从第 "+dataStart+" 条开始");
        page.setDataStart(dataStart);
        page.setAllDataSum(allDataSum);
        try {
            music = musicMapper.getMusicPage(page);
            if (music == null){
                result = Result.success(201,"没有数据或已经是尾页数据",null);
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

    //获取所有音乐
    @Override
    public Result findAll() {
        Result result;
        try {
            List<Music> musics = musicMapper.getAll();
            result = Result.success("成功获取所有音乐",musics);
        }catch (Exception e){
            e.printStackTrace();
            Map<String, Object> map = new HashMap<>();
            map.put("errMsg",e.getMessage());
            result = Result.fail("获取所有音乐失败",map);
        }
        return result;
    }

    //获取一首音乐
    @Override
    public Result getOneMusic(Music music) {
        Result result;
        try {
            Music oneMusic = musicMapper.getOneMusic(music);
            if (oneMusic==null){
                return Result.fail("找不到此音乐");
            }
            result = Result.success("成功获取音乐",oneMusic);
        }catch (Exception e){
            e.printStackTrace();
            Map<String, Object> map = new HashMap<>();
            map.put("errMsg",e.getMessage());
            result = Result.fail("获取音乐失败",map);
        }
        return result;
    }

    //添加音乐
    @Override
    public Result addMusic(Music music, MultipartFile cover, MultipartFile musicFile) {
        Result result;
        try {
            musicMapper.addMusic(music);//插入后获取音乐id到music类中
            String tip = "";//提示
            //上传文件并获取地址
            if (musicFile!=null){
                Result uploadResult = upload(musicFile,music,2);
                if(uploadResult.getCode()!=200){
                    removeMusic(music);
                    musicMapper.modifyAutoincrement(musicMapper.getMusicLastId());
                    return uploadResult;
                }
                String url = (String) uploadResult.getData();
                music.setUrl(url);
            }
            if (cover!=null){
                Result uploadResult = upload(cover,music,1);
                if(uploadResult.getCode()!=200){
                    removeMusic(music);
                    musicMapper.modifyAutoincrement(musicMapper.getMusicLastId());
                    return uploadResult;
                }
                String coverURL = (String) uploadResult.getData();
                music.setCoverURL(coverURL);
            }

            modifyMusic(music);//将文件地址存入到数据库

            result = Result.success(200,"音乐添加成功",tip);
        }catch (Exception e){
            e.printStackTrace();
            Map<String, Object> map = new HashMap<>();
            map.put("errMsg",e.getMessage());
            result = Result.fail("音乐添加失败",map);
        }
        return result;
    }


    //修改一首音乐
    public Result modifyMusic(Music music){
        Result result;
        try {
            musicMapper.modifyMusic(music);
            result = Result.success("修改音乐成功");
        }catch (Exception e){
            e.printStackTrace();
            result = Result.fail("修改音乐失败");
        }
        return result;
    }

    //删除音乐
    @Override
    public Result removeMusic(Music music) {
        Result result;
        Music aMusic = musicMapper.getOneMusic(music);
        try {
            if(aMusic==null)
                return new Result(501,"删除失败，未找到此音乐");
            musicMapper.removeMusic(music);

            //删除文件
            String relativePath = MusicController.class.getClassLoader().getResource("").getPath();//获取绝对路径
            relativePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);//处理字符问题
            String url = relativePath+"static/"+aMusic.getUrl();//获取音乐文件地址
            String coverURL = relativePath+"static/"+aMusic.getCoverURL();//获取音乐封面图片地址

            File musicFile = new File(url);//获取音乐文件名
            File coverImage = new File(coverURL);//获取音乐封面图片名
            String fileDeleteMsg = "文件删除情况：";
            if(musicFile.delete()){
                fileDeleteMsg += "音乐文件删除成功";
                System.out.println("音乐文件删除成功");
            }else{
                fileDeleteMsg += "音乐文件不存在";
                System.out.println("音乐文件不存在");
            }
            if(coverImage.delete()){
                fileDeleteMsg += "，音乐封面删除成功";
                System.out.println("音乐封面删除成功");
            }else{
                fileDeleteMsg += "，音乐封面不存在";
                System.out.println("音乐封面不存在");
            }

            result = Result.success(200,"音乐删除成功",fileDeleteMsg);
        }catch (Exception e){
            e.printStackTrace();
            Map<String, Object> map = new HashMap<>();
            map.put("errMsg",e.getMessage());
            result = Result.fail("音乐删除失败",map);
        }
        return result;
    }


    //文件上传，不更改文件名
    @Override
    public Result uploadFile(MultipartFile file) {
        return upload(file,null,0);
    }

    //音乐文件和音乐封面图片上传
    public Result upload(MultipartFile file,Music music,int code) {//code 0为自判定 1为图片 2为音频
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
        String savePath = "static/music/";//保存路径
        String path = relativePath+savePath;//拼接

        //文件后缀处理
        String fileSuffix = originFileName.substring(originFileName.lastIndexOf('.'));//文件后缀
        String[] supportImgSuffix = {".jpg",".png"};//支持的音乐封面图片后缀
        String[] supportAudioSuffix = {".mp3"};//支持的音频后缀
        //判断文件后缀

        //code为1，应为图片时; code为2，应为音频时
        if(code==1&&!Arrays.stream(supportImgSuffix).toList().contains(fileSuffix)){
            return Result.fail("文件上传错误，文件类型应为图片");
        }else if(code==2&&!Arrays.stream(supportAudioSuffix).toList().contains(fileSuffix)){
            return Result.fail("文件上传错误，文件类型应为音频");
        }

        boolean isImg = false;//是否为图片
        if (Arrays.stream(supportImgSuffix).toList().contains(fileSuffix)){
            isImg = true;
            path+="cover";
        }else if(Arrays.stream(supportAudioSuffix).toList().contains(fileSuffix)){
            path+="audio";
        }else{
            return Result.fail("不支持此文件");
        }

        //保存文件
        File realPath = new File(path);//创建文件夹
        if(!realPath.exists()){//文件夹不存在则创建文件夹
            realPath.mkdirs();
        }

        try {

            //改名
            String newFileName = originFileName;
            if (music!=null){
                newFileName = "mid_"+music.getMusicId()+fileSuffix;
            }

            file.transferTo(new File(path,newFileName));

            result = Result.success(200,"文件上传成功","music/"+(isImg?"cover/":"audio/")+newFileName);
        } catch (IOException e) {
            e.printStackTrace();
            result = Result.fail("文件上传失败",e.getMessage());
        }
        return result;
    }
}
