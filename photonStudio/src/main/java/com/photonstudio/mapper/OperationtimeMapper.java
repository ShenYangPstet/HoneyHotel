package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Operationtime;

public interface OperationtimeMapper {

	int getRowCount(@Param("dBname")String dBname, @Param("drid")Integer drid);

	List<Operationtime> findObject(@Param("dBname")String dBname, 
								   @Param("drid")Integer drid, 
								   @Param("startIndex")Integer startIndex, 
								   @Param("pageSize")Integer pageSize);

	int insertObject(@Param("dBname")String dBname, Operationtime operationtime);

	int deleteObjectById(@Param("dBname")String dBname, Integer... ids);

	int updateObject(@Param("dBname")String dBname, Operationtime operationtime);
	
}
