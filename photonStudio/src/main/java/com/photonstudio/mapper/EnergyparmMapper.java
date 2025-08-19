package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Energyparm;

public interface EnergyparmMapper {

	int getRowCount(@Param("dBname")String dBname);

	List<Energyparm> findObject(@Param("dBname")String dBname, @Param("startIndex")int startIndex, @Param("pageSize")Integer pageSize);

	int insertObject(@Param("dBname")String dBname, Energyparm energyparm);

	int deleteObjectById(@Param("dBname")String dBname, @Param("id")Integer id);

	int updateObject(@Param("dBname")String dBname, Energyparm energyparm);

}
