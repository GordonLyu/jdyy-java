package com.jdyy.controller;

import com.jdyy.commons.util.Result;
import com.jdyy.entity.vo.Page;
import org.springframework.web.bind.annotation.*;

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

    //获取所有音乐
    @GetMapping("/getAll")
    public Result getAllMusic(){
        return null;
    }

    //分页获取音乐
    @GetMapping("/getPage")
    public Result getUserPage(Page page){
        return null;
    }

    //添加音乐
    @PostMapping("/add")
    public Result addMusic(){
        return null;
    }

    //修改音乐
    @PostMapping("/modify")
    public Result modifyMusic(){
        return null;
    }


}
