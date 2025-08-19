package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Buildinfo;

public interface BuildinfoMapper {

	List<Buildinfo> findObjectByName(@Param("buildname")String buildname, 
									 @Param("appname")String appname, 
									 @Param("startIndex")Integer startIndex, 
									 @Param("pageSize") Integer pageSize);

	int insertObject(@Param(value = "dBname")String dBname, Buildinfo buildinfo);

	int deleteObjectById(@Param(value = "dBname")String dBname, @Param("ids")Integer...ids);

	int updateObject(@Param(value = "dBname")String dBname, Buildinfo buildinfo);

	int getRowCount(@Param(value = "dBname")String appname, String buildname);

	List<Buildinfo> findAll(@Param("dBname")String dBname);

	int findObjectByBuildid(@Param("dBname")String dBname, @Param("buildid")Integer buildid);

}
