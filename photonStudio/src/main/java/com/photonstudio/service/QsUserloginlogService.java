package com.photonstudio.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.QsUserloginlog;

public interface QsUserloginlogService {

	PageObject<QsUserloginlog> findObject(String dBname, String username, Integer pageCurrent, Integer pageSize,
			Date startTime, Date endTime);

	int saveObject(String dBname, QsUserloginlog qsUserloginlog, HttpServletRequest request);

	int deleteObjectById(String dBname, Integer... ids);

	int updateObject(String dBname, QsUserloginlog qsUserloginlog);

}
