package com.photonstudio.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.QsActivelog;

public interface QsActivelogMapper {

	int getRowCount(@Param("dBname")String dBname,
					@Param("username")String username, 
					@Param("startTime")Date startTime,
					@Param("endTime")Date endTime);

	List<QsActivelog> findObject(@Param("dBname")String dBname,@Param("username")String username, 
						@Param("startIndex")Integer startIndex,@Param("pageSize") Integer pageSize, 
						@Param("startTime")Date startTime,@Param("endTime")Date endTime);

	int insertObject(@Param("dBname")String dBname, QsActivelog qsActivelog);

	int deleteObjectById(@Param("dBname")String dBname,@Param("ids") Integer...ids);

	int updateObject(@Param("dBname")String dBname, QsActivelog qsActivelog);
	

}
