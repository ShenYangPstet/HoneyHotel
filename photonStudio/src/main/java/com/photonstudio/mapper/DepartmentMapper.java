package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Department;

public interface DepartmentMapper {

	List<Department> findObject(@Param("dBname") String dBname,@Param("fuid") Integer fuid, 
					@Param("startIndex")Integer startIndex,@Param("pageSize") Integer pageSize);
	
	int insertObject(@Param("dBname") String dBname,Department department);
	
	int deleteObjectById(@Param("dBname") String dBname,@Param("departmentid")Integer departmentid);
	
	int updateObject(@Param("dBname") String dBname,Department department);

	List<Integer> findChildrenId(@Param("dBname")String dBname,@Param("departmentid") Integer departmentid);

	int getRowCount(@Param("dBname")String dBname);
}
