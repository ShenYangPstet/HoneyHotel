package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Queryresult;

public interface QueryresultMapper {
	List<Queryresult> findObject(@Param("dBname")String dBname);
	int insertObject(@Param("dBname")String dBname, Queryresult queryresult);
	int deleteObjectById(@Param("dBname")String dBname,@Param("id") Integer id);
	int updateObject(@Param("dBname")String dBname, Queryresult queryresult);
}
