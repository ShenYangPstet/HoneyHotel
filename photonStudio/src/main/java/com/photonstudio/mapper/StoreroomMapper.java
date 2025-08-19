package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Storeroom;

public interface StoreroomMapper {

	int getRowCount(@Param("dBname") String dBname);

	List<Storeroom> findObject(@Param("dBname")String dBname,
			       @Param("startIndex")Integer startIndex,
			       @Param("pageSize") Integer pageSize);

	int insertObject(@Param("dBname")String dBname, Storeroom storeroom);

	int deleteObjectById(@Param("dBname")String dBname, @Param("ids")Integer... ids);

	int updateObject(@Param("dBname")String dBname, Storeroom storeroom);

}
