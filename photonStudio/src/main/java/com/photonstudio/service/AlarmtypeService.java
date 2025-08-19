package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Alarmtype;
import com.photonstudio.pojo.UserAlarmlevel;

public interface AlarmtypeService {

	List<Alarmtype> findAll(String dBname);

	int saveObject(String dBname, Alarmtype alarmtype);

	int deleteObject(String dBname, Integer... ids);

	int updateObject(String dBname, Alarmtype alarmtype);

	PageObject<Alarmtype> findObject(String dBname, Integer pageCurrent, Integer pageSize);

	int updateUserAlarm(String dBname, Integer userid, Integer[] ids);

	List<UserAlarmlevel> findUserAlarm(String dBname, Integer userid);
	
}
