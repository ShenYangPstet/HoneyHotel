package com.photonstudio.service;

import java.util.Date;
import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.QsLdLog;

public interface QsLdLogService {

	PageObject<QsLdLog> findObject(String dBname, Integer pageCurrent, Integer pageSize, Integer type, Integer state,
			Date startTime, Date endTime);

	int saveObject(String dBname, QsLdLog qsLdLog);

	int deleteObjectById(String dBname, Integer[] ids);

	int updateObject(String dBname, QsLdLog qsLdLog);

	List<QsLdLog> findAll(String dBname, Date startTime, Date endTime);
	
}
