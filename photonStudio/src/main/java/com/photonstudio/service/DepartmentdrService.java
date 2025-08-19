package com.photonstudio.service;

import java.util.List;

import com.photonstudio.pojo.Departmentdr;

public interface DepartmentdrService {

	List<Departmentdr> findObject(String dBname, Integer departmentid);

	int saveObject(String dBname, Integer departmentid, Integer... drids);

	int deleteObject(String dBname, Integer... ids);

}
