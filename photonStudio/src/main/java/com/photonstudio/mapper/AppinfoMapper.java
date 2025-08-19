package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Appinfo;

public interface AppinfoMapper {

	List<Appinfo> findObject(@Param("dBname") String dBname,
			                 @Param("startIndex")Integer startIndex,
			                 @Param("pageSize") Integer pageSize);

	int insertObject(@Param("dBname")String dBname, Appinfo appinfo);

	int deleteObject(@Param("dBname")String dBname,
					 @Param("appid") Integer appid);

	Appinfo findObjectById(@Param("dBname")String dBname,
						   @Param("appid") Integer appid);

	int updateObject(@Param("dBname")String dBname, Appinfo appinfo);

	int getRowCount(@Param("dBname")String dBname);

	List<Appinfo> findAppinfo(@Param("dBname")String dBname);

}