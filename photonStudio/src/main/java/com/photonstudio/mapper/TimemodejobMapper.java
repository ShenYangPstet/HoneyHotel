package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Timemodejob;

public interface TimemodejobMapper {

	List<Timemodejob> findObject(@Param("dBname")String dBname,@Param("timemodeid") Integer timemodeid,
			@Param("time") String time,@Param("startIndex")Integer startIndex, 
			@Param("pageSize")Integer pageSize);

	Timemodejob findObjectById(@Param("dBname")String dBname,@Param("id") Integer id);

	List<Timemodejob> findObjectByLdTjId(@Param("dBname")String dBname,@Param("ids") Integer... ldTjId);

	int updateObject(@Param("dBname")String dBname, Timemodejob timemodejob);

	List<Timemodejob> findObjectByUseTime(@Param("dBname")String dBname);

	int deleteObjectByIds(@Param("dBname")String dBname, Integer... ids);

	List<Timemodejob> findObjectByTimemodeids(@Param("dBname")String dBname, Integer[] ids);

	List<Timemodejob> findObjectByLdRwIds(@Param("dBname")String dBname, Integer[] ids);

}
