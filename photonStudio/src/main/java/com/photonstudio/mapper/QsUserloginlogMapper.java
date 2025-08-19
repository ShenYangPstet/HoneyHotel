package com.photonstudio.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.QsUserloginlog;

public interface QsUserloginlogMapper {

	int getRowCount(@Param("dBname") String dBname,@Param("username") String username, 
					@Param("startTime")Date startTime,@Param("endTime") Date endTime);

	List<QsUserloginlog> findObject(@Param("dBname")String dBname,@Param("username") String username, 
			@Param("startIndex")Integer startIndex,@Param("pageSize") Integer pageSize, 
			@Param("startTime")Date startTime,@Param("endTime")Date endTime);

	int insertObject(@Param("dBname")String dBname, QsUserloginlog qsUserloginlog);

	int deleteObjectById(@Param("dBname")String dBname,@Param("ids") Integer... ids);
	

}
