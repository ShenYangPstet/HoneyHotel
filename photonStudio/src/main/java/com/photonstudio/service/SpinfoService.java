package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Spinfo;

public interface SpinfoService {
	PageObject<Spinfo>	findObject(String dBname, Integer startIndex, Integer pageSize);

	int saveObject(String dBname, Spinfo spinfo);

	int deleteObject(String dBname, Integer...ids);

	int updateObject(String dBname, Spinfo spinfo);

	List<Spinfo> findAll(String dBname);

	void importObjects(String dBname, List<Spinfo> list);

	List<Spinfo> findObjectByIds(String dBname, Integer[] ids);
}
