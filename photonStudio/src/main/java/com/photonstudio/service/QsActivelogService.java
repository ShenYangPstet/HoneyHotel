package com.photonstudio.service;

import java.util.Date;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.QsActivelog;

public interface QsActivelogService {

	PageObject<QsActivelog> findObject(String dBname, String username, Integer pageCurrent, Integer pageSize,
			Date startTime, Date endTime);

	int saveObject(String dBname, QsActivelog qsActivelog);

	int deleteObject(String dBname, Integer... ids);

	int updateObject(String dBname, QsActivelog qsActivelog);

}
