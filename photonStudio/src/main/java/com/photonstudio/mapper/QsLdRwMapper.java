package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.QsLdRw;

public interface QsLdRwMapper {

	int getRowCount(@Param("dBname")String dBname);

	List<QsLdRw> findObject(@Param("dBname")String dBname,
							@Param("startIndex") Integer startIndex, 
							@Param("pageSize") Integer pageSize);

	int insertObject(@Param("dBname")String dBname, QsLdRw qsLdRw);

	int deleteObjectById(@Param("dBname")String dBname,@Param("ids") Integer... ids);

	int updateObject(@Param("dBname")String dBname, QsLdRw qsLdRw);

	int findObjectById(@Param("dBname")String dBname, Integer id);

	List<QsLdRw> findMoshiObject(@Param("dBname")String dBname, @Param("rwType")Integer rwType);

	QsLdRw findObjectByldRwId(@Param("dBname")String dBname,@Param("ldRwId") Integer ldRwId);

	List<QsLdRw> findObjectByIssuspend(@Param("dBname")String dBname,@Param("issuspend") Integer issuspend);

	List<QsLdRw> findObjectByIds(@Param("dBname")String dBname, Integer[] ldrwids);
}

