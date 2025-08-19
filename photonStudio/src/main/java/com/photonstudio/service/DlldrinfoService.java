package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Dlldrinfo;
import com.photonstudio.pojo.Drllinfo;
import com.photonstudio.pojo.Drlltype;

public interface DlldrinfoService {

	PageObject<Dlldrinfo> findObject(String dBname, String drname, Integer pageCurrent, Integer pageSize);

	int saveObject(String dBname, Dlldrinfo dlldrinfo);

	int deleteObjectById(String dBname, Integer[] ids);

	int updateObject(String dBname, Dlldrinfo dlldrinfo);

	List<Dlldrinfo> findDlldrinfo(String dBname, String drname);

	void importObjects(String dBname, List<Dlldrinfo> list);

	List<Drllinfo> findDrllinfo(String dBname);

	List<Drlltype> findDrlltypeBydrid(String dBname, Integer drid);

}
