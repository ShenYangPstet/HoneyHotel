package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Elemttype;

public interface ElemttypeMapper {

	int getRowCount(@Param("dBname")String dBname);

	List<Elemttype> findObject(@Param("dBname")String dBname, 
			                   @Param("startIndex")Integer startIndex, 
			                   @Param("pageSize")Integer pageSize, 
			                   @Param("elemttypeid") Integer elemttypeid);

	int insertObject(@Param("dBname")String dBname, Elemttype elemttype);

	int deleteObjectById(@Param("dBname")String dBname, @Param("ids")Integer...ids);

	int updateObject(@Param("dBname")String dBname, Elemttype elemttype);

	List<Elemttype> findObjectByTypeid(@Param("dBname")String dBname, @Param("typeid")Integer typeid);

	Elemttype findObjectById(@Param("dBname")String dBname, @Param("elemttypeid")Integer elemttypeid);

}
