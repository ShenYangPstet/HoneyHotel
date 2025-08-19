package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Appuser;

public interface AppuserMapper {

	List<Appuser> findObject(@Param("dBname") String dBname,
							@Param("username")String username,
							@Param("startIndex")Integer startIndex,
							@Param("pageSize")Integer pageSize);

	int getRowCount(@Param("dBname")String dBname,@Param("username") String username);
	
	int insertObject(@Param("dBname")String dBname,Appuser appuser);
	
	int deleteObjectById(@Param("dBname")String dBname,Integer...ids);
	
	int updateObject(@Param("dBname")String dBname,Appuser appuser);

	Appuser findObjectByUserid(@Param("dBname")String dBname, Integer userid);
}
