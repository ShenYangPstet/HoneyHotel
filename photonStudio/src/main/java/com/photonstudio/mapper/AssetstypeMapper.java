package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Assetstype;

public interface AssetstypeMapper {

	int getRowCount(@Param("dBname")String dBname,@Param("assetstypename") String assetstypename);

	List<Assetstype> findObjectByName(@Param("dBname")String dBname, 
									@Param("assetstypename")String assetstypename, 
									@Param("startIndex")Integer startIndex, 
									@Param("pageSize")Integer pageSize);

	int insertObject(@Param("dBname")String dBname, Assetstype assetstype);

	int deleteObjectById(@Param("dBname")String dBname, Integer...ids);

	int updateObject(@Param("dBname")String dBname, Assetstype assetstype);

	int findObjectById(@Param("dBname")String dBname,@Param("id") Integer id);
	

}
