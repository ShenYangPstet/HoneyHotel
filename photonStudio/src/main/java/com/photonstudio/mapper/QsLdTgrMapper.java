package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.QsLdTgr;

public interface QsLdTgrMapper {

	int getRowCount(@Param("dBname") String dBname,@Param("tjType") Integer tjType);

	List<QsLdTgr> findObject(@Param("dBname")String dBname, @Param("tjType")Integer tjType, Integer startIndex, Integer pageSize);

	int insertObject(@Param("dBname")String dBname, QsLdTgr qsLdTgr);

	int deleteObjectById(@Param("dBname")String dBname, Integer... ids);

	int updateObject(@Param("dBname")String dBname, QsLdTgr qsLdTgr);

	int findObjectById(@Param("dBname")String dBname,@Param("id") Integer id);
	
	List<QsLdTgr> findObjectByLdRwIdAndTjType(@Param("dBname")String dBname,@Param("ldRwId") Integer ldRwId,@Param("tjType") Integer tjType);

	QsLdTgr findObjectByldTjId(@Param("dBname")String dBname, @Param("ldTjId")Integer ldTjId);

	void deleteObjectByRwId(@Param("dBname")String dBname, Integer[] ids);
	
}
