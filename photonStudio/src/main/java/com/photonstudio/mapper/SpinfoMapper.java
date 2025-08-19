package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Spinfo;

public interface SpinfoMapper {
	List<Spinfo> findObject(@Param("dBname")String dBname,
			@Param("startIndex")Integer startIndex,@Param("pageSize") Integer pageSize);
	 int getRowCount(@Param("dBname")String dBname);
	int insertObject(@Param("dBname")String dBname, Spinfo spinfo);
	int deleteObjectById(@Param("dBname")String dBname, @Param("ids")Integer... ids);
	int updateObject(@Param("dBname")String dBname, Spinfo spinfo);
	int findObjectById(@Param("dBname")String dBname,@Param("id") Integer id);
	List<Spinfo> findObjectByIds(@Param("dBname")String dBname, Integer[] ids);
	
}
