package com.photonstudio.service;

import java.util.List;
import java.util.Map;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Appinfo;
import com.photonstudio.pojo.Appsysuser;
import com.photonstudio.pojo.Usertoken;

public interface AppsysuserService {

	Map<String, Object> findUserByUP(Appsysuser user);

	Usertoken findUsertokenByToken( String token);

	Appsysuser findAppsysuserByUsername( String username);

	PageObject<Appsysuser> findUserByName(String name, Integer pageCurrent, Integer pageSize);

	int saveObject(Appsysuser user);

	int deleteObjectById(Integer...ids);

	int updateObject(Appsysuser user);

	boolean findCheckUser(String username);

	String getTokenByUsername(String username);

	List<Appsysuser> findAll();

	Appinfo queryAppinfoByAppname(String dBname, String appName);

	int findUserByPassword(String username, String password);

	int updatePassword(String username, String newpassword);

	

	
	
}
