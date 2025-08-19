package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Queryconditioninfo;

public interface QueryconditioninfoMapper {

	List<Queryconditioninfo> findObjectByconditionId(@Param("dBname")String dBname,@Param("conditionId") Integer conditionId);
	List<Queryconditioninfo> findObject(@Param("dBname")String dBname,@Param("conditionId") Integer conditionId,
			@Param("startIndex")Integer startIndex, @Param("pageSize")Integer pageSize);
	int getRowCount(@Param("dBname")String dBname,@Param("conditionId") Integer conditionId);
	int insertObject(@Param("dBname")String dBname,Queryconditioninfo queryconditioninfo);
	int deleteObjectById(@Param("dBname")String dBname,@Param("id") Integer id);
	int deleteObjectByConditionId(@Param("dBname")String dBname,@Param("conditionId") Integer conditionId);
	int updateObject(@Param("dBname")String dBname,Queryconditioninfo queryconditioninfo);
}
