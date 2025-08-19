package com.photonstudio.service;

import java.util.List;

import com.photonstudio.pojo.Timemodejob;

public interface TimemodejobService {

	List<Timemodejob> findObjectByTimemodeid(String dBname, Integer timemodeid);

	Timemodejob findObjectById(String dBname, Integer id);

	List<Timemodejob> findObjectByUseTime(String dBname);

	int deleteObjectById(String dBname,Integer...ids);

	void deleteObjectByTjIds(String dBname, Integer... ids);

	void deleteObjectByTimemodeids(String dBname, Integer... timemodeids);

	void deleteObjectByRwId(String dBname, Integer... ids);


	
}
