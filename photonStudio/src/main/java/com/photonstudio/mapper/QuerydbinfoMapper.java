package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Querydbinfo;

/**
 * 查询参数 数据库类型，sql，url等
 * @author 16526
 *
 */

public interface QuerydbinfoMapper {
	//查询
	List<Querydbinfo> findObject(@Param("dBname") String dBname,@Param("startIndex")Integer startIndex, 
			@Param("pageSize")Integer pageSize);
	int insertObject(@Param("dBname")String dBname,Querydbinfo querydbinfo);
	int deleteObjectById(@Param("dBname")String dBname,Integer id);
	int updateObject(@Param("dBname")String dBname,Querydbinfo querydbinfo);
	Querydbinfo findObjectById(String dBname, @Param("id")Integer id);
	int getRowCount(@Param("dBname")String dBname);
}
