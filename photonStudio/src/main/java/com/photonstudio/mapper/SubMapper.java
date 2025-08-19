package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Sub;

public interface SubMapper {

	List<Sub> findAll(@Param("dBname")String dBname);

	int insertObject(@Param("dBname")String dBname, Sub sub);

	int deleteObjectById(@Param("dBname")String dBname, @Param("ids")Integer... ids);

	int updateObject(@Param("dBname")String dBname, Sub sub);

	int getRowCount(@Param("dBname")String dBname);

	List<Sub> findObject(@Param("dBname")String dBname, 
						 @Param("startIndex")Integer startIndex, 
						 @Param("pageSize")Integer pageSize);

}
