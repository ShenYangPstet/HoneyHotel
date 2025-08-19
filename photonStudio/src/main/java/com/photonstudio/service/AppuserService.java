package com.photonstudio.service;

import java.util.Date;
import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Appuser;

public interface AppuserService {

	List<Appuser> findAll(String dBname,String username);

	PageObject<Appuser> findObject(String dBname,String username ,Integer pageCurrent, Integer pageSize);

	int saveObject(String dBname, Appuser appuser);

	int deleteObject(String dBname, Integer appid, Integer... ids);

	int updateObject(String dBname, Appuser appuser, Integer appid);

	int saveAppuser(String dBname, Appuser appuser, Integer appid, Date licensetime);

	Appuser findObjectByUserid(String dBname, Integer userid);

}
