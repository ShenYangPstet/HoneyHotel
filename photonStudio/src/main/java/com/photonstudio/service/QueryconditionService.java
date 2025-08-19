package com.photonstudio.service;

import java.util.List;

import com.photonstudio.pojo.Querycondition;

public interface QueryconditionService {

	List<Querycondition> findObject(String dBname);

	int saveObject(String dBname, Querycondition querycondition);

	int deleteObjectByid(String dBname, Integer id);

	int updateObject(String dBname, Querycondition querycondition);

}
