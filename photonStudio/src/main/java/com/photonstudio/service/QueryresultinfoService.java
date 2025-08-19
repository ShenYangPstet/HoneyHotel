package com.photonstudio.service;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Queryresultinfo;

public interface QueryresultinfoService {

	PageObject<Queryresultinfo> findObject(String dBname, Integer resultId, Integer pageCurrent, Integer pageSize);

	int saveObject(String dBname, Queryresultinfo queryresultinfo);

	int deleteObjectByid(String dBname, Integer id);

	int updateObject(String dBname, Queryresultinfo queryresultinfo);

}
