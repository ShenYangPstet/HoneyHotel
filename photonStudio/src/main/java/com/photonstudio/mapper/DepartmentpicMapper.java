package com.photonstudio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.photonstudio.pojo.Departmentpic;
import com.photonstudio.pojo.Pic;

public interface DepartmentpicMapper {
	List<Departmentpic> findObject(@Param("dBname")String dBname,@Param("departmentid")Integer departmentid);
	
	int insertObject(@Param("dBname")String dBname,@Param("departmentid")Integer departmentid,
			@Param("pictype")Integer pictype, @Param("picids")Integer... picids);
	
	int deleteObjectById (@Param("dBname")String dBname,@Param("ids")Integer...ids);
	
	List<Pic> findPicByDepartmentid(@Param("dBname")String dBname,Integer departmentid);
}
