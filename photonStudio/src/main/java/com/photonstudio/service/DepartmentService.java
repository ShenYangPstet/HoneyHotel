package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Department;

public interface DepartmentService {

	List<Department> findObjectByFuid(String dBname, Integer fuid);

	List<Department> findAll(String dBname);

	int insertObject(String dBname, Department department);

	int deleteObjectById(String dBname, Integer departmentid);

	int updateObject(String dBname, Department department);

	PageObject<Department> findObject(String dBname, Integer pageCurrent, Integer pageSize);

}
