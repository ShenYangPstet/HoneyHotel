package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Appmanager;

public interface AppmanagerService {

	PageObject<Appmanager> findObjectByApptypeid(
			Integer apptypeid ,Integer userid,String region, String city, Integer pageCurrent,
			Integer pageSize, String state);
	int deleteObjectsById(Integer appid,String dBname);
	int saveObject(Appmanager appmanager);
	int updateObject(Appmanager appmanager);
	int findCount(String apptypeid);
	int initObject(String dBname, Integer appid);
	int removeObject(String state,Integer...appids);
	boolean findCheckappmanager(String appName);
	List<Appmanager> findObjectByUesrid(Integer userid);
	Appmanager findObjectByAppid(Integer appid);
	List<Appmanager> findObjectByState(String appstate, String state);
	List<Appmanager> findAllByApptypeid(Integer userid, String region, String city);
	List<Appmanager> findAllByAppids(Integer[] appids);
	int getRowCountByLicense();
	List<Appmanager> findAllByAppidsAppstate(Integer[] appids, String appstate);
	
	
}
