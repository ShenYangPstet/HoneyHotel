package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Elemttypemode;

public interface ElemttypemodeMapper {

	int getRowCount(@Param("dBname")String dBname,@Param("elemttypeid") Integer elemttypeid);

	List<Elemttypemode> findObject(@Param("dBname")String dBname, 
								   @Param("elemttypeid")Integer elemttypeid, 
								   @Param("startIndex")Integer startIndex, 
								   @Param("pageSize")Integer pageSize);

	int insertObject(@Param("dBname")String dBname, Elemttypemode elemttypemode);

	int deleteObjectById(@Param("dBname")String dBname,@Param("ids") Integer...ids);

	int updateObject(@Param("dBname")String dBname, Elemttypemode elemttypemode);

	List<Elemttypemode> findElemttypemodeById(@Param("dBname")String dBname, @Param("id")Integer id);

	void synchroElemt(@Param("dBname")String dBname);
	
}
