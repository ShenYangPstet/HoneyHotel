package com.photonstudio.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.QsLdJgr;
import com.photonstudio.pojo.QsLdLog;

public interface QsLdLogMapper {

	int getRowCount(@Param("dBname") String dBname,@Param("type") Integer type, 
					@Param("state")Integer state,@Param("startTime") Date startTime, 
					@Param("endTime")Date endTime);

	List<QsLdLog> findObject(@Param("dBname")String dBname,@Param("type") Integer type, 
			@Param("state")Integer state,@Param("startIndex") Integer startIndex, 
			@Param("pageSize")Integer pageSize,@Param("startTime") Date startTime, 
			@Param("endTime")Date endTime);

	int insertObject(@Param("dBname")String dBname, QsLdLog qsLdLog);

	int deleteObjectById(@Param("dBname")String dBname, Integer[] ids);

	int updateObject(@Param("dBname")String dBname, QsLdLog qsLdLog);

	List<QsLdLog> findAllByTime(String dBname, Date startTime, Date endTime);

	int insertObjectByQsLdJgr(@Param("dBname")String dBname, QsLdJgr qsLdJgr,@Param("tjid") Integer tjid);
	
}
