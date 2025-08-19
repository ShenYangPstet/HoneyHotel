package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.QsLdRw;

public interface QsLdRwService {

	PageObject<QsLdRw> findObject(String dBname, Integer pageCurrent, Integer pageSize);

	int saveObject(String dBname, QsLdRw qsLdRw);

	int deleteObjectById(String dBname, Integer... ids);

	int updateObject(String dBname, QsLdRw qsLdRw);

	List<QsLdRw> findAllByRwType(String dBname, Integer rwType);

	void importObjects(String dBname, List<QsLdRw> list);

	List<QsLdRw> findMoshiObject(String dBname, Integer rwType);

	QsLdRw findObjectById(String dBname, Integer ldRwId);

	boolean startJobByLdRwId(String dBname, Integer ldRwId,Integer appid, QsLdRw qsLdRw);

	boolean deleteRegJob(String dBname, Integer ldRwId);

	List<QsLdRw> findObjectByIssuspend(String dBname, Integer issuspend);


	List<QsLdRw> findObjectByIds(String dBname, Integer[] ldrwids);

	List<QsLdRw> findAll(String dBname);

}
