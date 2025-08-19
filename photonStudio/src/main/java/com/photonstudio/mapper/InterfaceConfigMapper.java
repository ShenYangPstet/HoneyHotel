package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.InterfaceConfig;

public interface InterfaceConfigMapper {

	int getRowCount(@Param("explain") String explain,@Param("interfaceType") Integer interfaceType);

	List<InterfaceConfig> findObject(@Param("explain") String explain,@Param("interfaceType")Integer interfaceType, 
			@Param("startIndex")Integer startIndex, @Param("pageSize")Integer pageSize);

	int insertObject(@Param("interfaceConfig")InterfaceConfig interfaceConfig);

	int deleteObject(@Param("ids")Integer[] ids);

	int updateObject( @Param("interfaceConfig")InterfaceConfig interfaceConfig);
	
}
