package com.photonstudio.service;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Queryconditioninfo;

public interface QueryconditioninfoService {

	PageObject<Queryconditioninfo> findObject(String dBname, Integer conditionId, Integer pageCurrent, Integer pageSize);

	int deleteObjectByid(String dBname, Integer id);

	int updateObject(String dBname, Queryconditioninfo queryconditioninfo);

	int saveObject(String dBname, Queryconditioninfo queryconditioninfo);

}
