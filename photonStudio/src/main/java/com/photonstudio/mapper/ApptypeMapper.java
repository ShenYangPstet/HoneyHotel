package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Apptype;

public interface ApptypeMapper {

	List<Apptype> findAll();

	

	int insertObject(Apptype apptype);



	int deleteObjectById(@Param(value = "ids")Integer... ids);



	int updateObject(Apptype apptype);



	int getRowCount();



	List<Apptype> findObject(@Param("startIndex")Integer startIndex, 
						     @Param("pageSize")Integer pageSize);
	
	
}
