package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.DateCalendar;
import com.photonstudio.pojo.QsLdTgr;

public interface QsLdTgrService {

	PageObject<QsLdTgr> findObject(String dBname, Integer tjType, Integer pageCurrent, Integer pageSize);

	int saveObject(String dBname, QsLdTgr qsLdTgr);

	int deleteObjectById(String dBname, Integer tjType, Integer... ids);

	int updateObject(String dBname, QsLdTgr qsLdTgr, Integer appid);

	void importObjects(String dBname, Integer tjType, List<QsLdTgr> list);

	List<QsLdTgr> findAllByRwType(String dBname, Integer tjTypes);

	List<DateCalendar> findDate(String year,String month);

	int savebydate(String dBname, String tgName, Integer rwid, String dateArry);

	List<QsLdTgr> findObjectByLdRwIdAndTjType(String dBname, Integer ldRwId, Integer tjType);

	QsLdTgr findObjectByldTjId(String dBname, Integer ldTjId);

	void deleteObjectByRwId(String dBname, Integer...ids);


}
