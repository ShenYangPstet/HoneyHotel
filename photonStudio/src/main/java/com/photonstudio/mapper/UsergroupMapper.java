package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Usergroup;

public interface UsergroupMapper {

	List<Usergroup> findAll(@Param("dBname")String dBname);

	int getRowCount(@Param("dBname")String dBname);

	List<Usergroup> findObject(@Param("dBname")String dBname,
						Integer startIndex, Integer pageSize);

	int updateObject(@Param("dBname")String dBname, Usergroup usergroup);

	int deleteObjectById(@Param("dBname")String dBname, Integer... ids);

	int insertObject(@Param("dBname")String dBname, Usergroup usergroup);

	Usergroup findObjectById(@Param("dBname")String dBname,@Param("id") Integer id);

	

}
