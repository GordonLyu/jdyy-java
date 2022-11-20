package com.jdyy.mapper;

import com.jdyy.entity.Music;
import com.jdyy.entity.vo.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author LvXinming
 * @since 2022/11/11
 */

@Mapper
public interface MusicMapper {
    //分页
    @Select("select * from music limit #{dataStart},#{pageSize};")
    @ResultMap({"musicMap"})
    List<Music> getMusicPage(Page<Music> page);

    //获取所有音乐
    @Select("select * from music;")
    @ResultMap({"musicMap"})
    List<Music> getAll();

    //获取一首音乐
    @Select("select * from music where music_id = #{musicId} or music_name = #{musicName};")
    @ResultMap({"musicMap"})
    Music getOneMusic(Music music);

    //获取所有音乐数
    @Select("select count(music_id) from music;")
    int countMusic();

    //添加一首音乐
    @Insert("insert into music(music_name, cover_url, url, author) values(#{musicName},#{coverURL},#{url},#{author});")
    @SelectKey(statement = {"SELECT LAST_INSERT_ID() as musicId"}, keyProperty = "musicId", before = false, resultType = int.class)
    void addMusic(Music music);

    //修改一首音乐
    void modifyMusic(Music music);

    //删除一首音乐
    @Delete("delete from music where music_id = #{musicId};")
    void removeMusic(Music music);
}
