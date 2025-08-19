package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Queryresultinfo;

public interface QueryresultinfoMapper {

	List<Queryresultinfo> findObject(@Param("dBname")String dBname,@Param("resultId") Integer resultId,
			@Param("startIndex")Integer startIndex, @Param("pageSize")Integer pageSize);

	List<Queryresultinfo> findObjectByResultId(@Param("dBname")String dBname,@Param("resultId")Integer resultId,
			@Param("startIndex")Integer startIndex, @Param("pageSize")Integer pageSize);
	int getRowCount(@Param("dBname")String dBname,@Param("resultId")Integer resultId);
	int insertObject(@Param("dBname")String dBname,Queryresultinfo queryresultinfo);
	int deleteObjectById(@Param("dBname")String dBname,@Param("id")Integer id);
	int deleteObjectByResultId(@Param("dBname")String dBname,@Param("resultId")Integer resultId);
	int updateObject(@Param("dBname")String dBname,Queryresultinfo queryresultinfo);

}
