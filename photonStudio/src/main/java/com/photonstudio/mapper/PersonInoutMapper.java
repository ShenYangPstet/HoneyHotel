package com.photonstudio.mapper;

import com.photonstudio.pojo.PersonInout;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PersonInoutMapper {
    int getRowCount(String dBname, Integer type, Integer cardCode, Date startTime, Date endTime);

    List<PersonInout> findObject(String dBname, Integer type, Integer cardCode, Date startTime, Date endTime, int startIndex, Integer pageSize);

    int insertObject(@Param("dBname") String dBname, PersonInout personInout);
}
