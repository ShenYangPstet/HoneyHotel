package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Buildinfo;

public interface BuildinfoService {

	PageObject<Buildinfo> findObjectByName(String buildname, String appname, Integer startIndex, Integer pageSize);

	int saveObject(String appname, Buildinfo buildinfo);

	int deleteObjectById(String dBname, Integer...ids);

	int updateObject(String dBname, Buildinfo buildinfo);

	List<Buildinfo> findAll(String dBname);

	void importObjects(String dBname, List<Buildinfo> list);

	

}
