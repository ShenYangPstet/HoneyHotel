package com.photonstudio.service;

import java.util.Map;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Querydbinfo;

public interface QuerydbinfoService {

	SysResult findObjectByOtherdb(String dBname, Integer id, Map<String, String> param);

	PageObject<Querydbinfo> findObject(String dBname, Integer pageCurrent, Integer pageSize);

	int saveObject(String dBname, Querydbinfo querydbinfo);

	int deleteObjectByid(String dBname, Integer id);

	int updateObject(String dBname, Querydbinfo querydbinfo);

	Querydbinfo findObjectById(String dBname, Integer id);

	SysResult findObjectByOtherdb23(String dBname, Integer id, Map<String, String> param);

}
