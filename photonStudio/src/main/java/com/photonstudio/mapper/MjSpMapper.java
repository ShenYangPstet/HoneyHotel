package com.photonstudio.mapper;

import com.photonstudio.pojo.MjSp;
import org.apache.ibatis.annotations.Param;

public interface MjSpMapper {
    String findMjID(@Param("dBname") String dBname, @Param("mjid") String mjid);

    void updateMjSp(@Param("dBname") String dBname, @Param("mjsp") MjSp mjsp);

    void insertMjSp(@Param("dBname") String dBname, @Param("mjsp") MjSp mjsp);

    void deleteMjSp(@Param("dBname") String dBname, @Param("mjid") String mjid);

    String findSpID(@Param("dBname") String dBname, @Param("mjid") String mjid);
}
