package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Dlldrinfo;
import com.photonstudio.pojo.Drllinfo;
import com.photonstudio.pojo.Drlltype;

public interface DlldrinfoMapper {

	int getRowCount(@Param("dBname")String dBname,@Param("drname") String drname);

	List<Dlldrinfo> findObject(@Param("dBname")String dBname,@Param("drname") String drname,
				@Param("startIndex") Integer startIndex,@Param("pageSize") Integer pageSize);

	int insertObject(@Param("dBname")String dBname, Dlldrinfo dlldrinfo);

	int deleteObjectById(@Param("dBname")String dBname, Integer[] ids);

	int updateObject(@Param("dBname")String dBname, Dlldrinfo dlldrinfo);

	int findObjectById(@Param("dBname")String dBname,@Param("drid") Integer drid);

	List<Drllinfo> findDrllinfo(@Param("dBname")String dBname);

	List<Drlltype> findDrlltypeBydrid(String dBname, Integer drid);
	
}
