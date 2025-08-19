package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.QsLdJgr;

public interface QsLdJgrMapper {

	int getRowCount(@Param("dBname")String dBname,@Param("ldLxId") Integer ldLxId);

	List<QsLdJgr> findObject(@Param("dBname")String dBname,@Param("ldLxId") Integer ldLxId, 
			@Param("startIndex")Integer startIndex,@Param("pageSize") Integer pageSize);

	int insertObject(@Param("dBname")String dBname, QsLdJgr qsLdJgr);

	int deleteObjectById(@Param("dBname")String dBname, Integer... ids);

	int updateObject(@Param("dBname")String dBname, QsLdJgr qsLdJgr);

	int findObjectById(@Param("dBname")String dBname,@Param("id") Integer id);

	List<QsLdJgr> findObjectByldRwId(@Param("dBname")String dBname,@Param("ldRwId") Integer ldRwId);

	void deleteObjectByRwId(@Param("dBname")String dBname,@Param("ids") Integer... ldRwid);

	QsLdJgr findObjectByLdJgId(@Param("dBname")String dBname,@Param("id") Integer ldJgId);
	
}
