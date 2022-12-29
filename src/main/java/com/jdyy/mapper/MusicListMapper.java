package com.jdyy.mapper;

import com.jdyy.entity.Music;
import com.jdyy.entity.MusicList;
import com.jdyy.entity.vo.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MusicListMapper {

    //分页获取歌单
    @Select("select * from list limit #{dataStart},#{pageSize};")
    @ResultMap({"musicListMap"})
    List<MusicList> getListPage(Page<MusicList> page);


    //分页
    @Select("select music.* from music join music_list on mid=music_id where lid=#{lid} limit #{page.dataStart},#{page.pageSize};")
    @ResultMap({"musicMap"})
    List<Music> getMusicListPage(Page<Music> page,Integer lid);

    //查询所有歌单
    @Select("select * from list;")
    @ResultMap({"musicListMap"})
    List<MusicList> getListAll();


    //添加一个歌单
    @Insert("insert into list(list_name, cover, creator,createTime,detail) values(#{listName},#{cover},#{creator},#{createTime},#{detail});")
    //插入后获取音乐id
    void addMusicList(MusicList musicList);

    @Select("SELECT max(lid) from list;" )
    int getLid();

    //添加一首音乐进歌单
    @Insert("insert into music_list values(#{lid},#{mid})")
    void addMusicToList(Integer lid,Integer mid);


    //获取一条歌单
    @Select("select * from list where lid = #{lid};")
    @ResultMap({"musicListMap"})
    MusicList getOneMusicList(Integer lid);


    //修改一首音乐
    void modifyMusicList(MusicList musicList);

    //删除一条歌单
    @Delete("delete from list where lid = #{lid};")
    void removeMusicList(Integer lid);


    //删除一首音乐和歌单的记录
    @Delete("delete from music_list where lid = #{lid} and mid=#{mid};")
    void removeMusicInList(Integer lid,Integer mid);

    //删除一首歌单的记录
    @Delete("delete from music_list where lid = #{lid};")
    void removeListRecord(Integer lid);


    //获取所有音乐数
    @Select("select count(*) from list;")
    int countMusic();

    //获取当前所有音乐数
    @Select("select count(*) from music join music_list on mid=music_id where lid=#{lid};")
    int countListMusic(Integer lid);
    /**
     * 修改自增
     */
    @Update("alter table list AUTO_INCREMENT=#{num};")
    void modifyAutoincrement(int num);
}
