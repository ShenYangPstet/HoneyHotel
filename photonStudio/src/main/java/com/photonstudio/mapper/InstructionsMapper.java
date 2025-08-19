package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Instructions;

public interface InstructionsMapper {

	int getRowCount(@Param("dBname")String dBname, 
					@Param("instructionsTypeid")Integer instructionsTypeid);

	List<Instructions> findObject(@Param("dBname")String dBname, 
			                      @Param("instructionsTypeid")Integer instructionsTypeid, 
			                      @Param("startIndex")Integer startIndex, 
			                      @Param("pageSize")Integer pageSize);

	int insertObject(@Param("dBname")String dBname, Instructions instructions);

	Instructions findObjectById(@Param("dBname")String dBname,@Param("id") Integer id);

	int deleteObjectById(@Param("dBname")String dBname,@Param("id") Integer id);
														
	int updateObject(@Param("dBname")String dBname, Instructions instructions);

}
