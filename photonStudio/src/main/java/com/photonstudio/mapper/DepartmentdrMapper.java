package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Departmentdr;

public interface DepartmentdrMapper {

	int insertObject(@Param("dBname") String dBname,@Param("departmentid") Integer departmentid, Integer... drids);

	int getRowCount(@Param("dBname") String dBname,@Param("departmentid") Integer departmentid);

	List<Departmentdr> findObject(@Param("dBname")String dBname,
								  @Param("departmentid") Integer departmentid);

	int deleteObjectById(@Param("dBname")String dBname, Integer... ids);

}
