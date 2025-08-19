package com.photonstudio.service;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Appinfo;

public interface AppinfoService {

	PageObject<Appinfo> findObject(String dBname, Integer pageCurrent, Integer pageSize);

	int saveObject(String dBname, Appinfo appinfo);

	int deleteObject(String dBname, Integer appid);

	Appinfo findObjectById(String dBname, Integer appid);

	int updateObject(String dBname, Appinfo appinfo);

	int findAppinfo(String dBname);

}
