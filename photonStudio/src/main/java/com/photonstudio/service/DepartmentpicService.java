package com.photonstudio.service;

import java.util.List;

import com.photonstudio.pojo.Departmentpic;
import com.photonstudio.pojo.Pic;

public interface DepartmentpicService {

	List<Departmentpic> findObkect(String dBname, Integer departmentid);

	int saveObject(String dBname, Integer departmentid, Integer... picids);

	int deleteObject(String dBname, Integer... ids);

	List<Pic> findPicByDepartmentid(String dBname, Integer departmentid);

}
