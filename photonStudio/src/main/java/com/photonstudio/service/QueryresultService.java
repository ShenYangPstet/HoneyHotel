package com.photonstudio.service;

import java.util.List;

import com.photonstudio.pojo.Queryresult;

public interface QueryresultService {

	List<Queryresult> findObject(String dBname);

	int saveObject(String dBname, Queryresult queryresult);

	int deleteObjectByid(String dBname, Integer id);

	int updateObject(String dBname, Queryresult queryresult);

}
