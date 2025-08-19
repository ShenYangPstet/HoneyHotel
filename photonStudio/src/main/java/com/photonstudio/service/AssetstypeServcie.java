package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Assetstype;

public interface AssetstypeServcie {
	
	PageObject<Assetstype> findObjectByName(String dBname, String assetstypename, Integer pageCurrent, Integer pageSize);

	int saveObject(String dBname, Assetstype assetstype);

	int deleteObject(String dBname, Integer...ids);

	int updateObject(String dBname, Assetstype assetstype);

	List<Assetstype> findAll(String dBname);

	void importObject(String dBname, List<Assetstype> list);
	
}
