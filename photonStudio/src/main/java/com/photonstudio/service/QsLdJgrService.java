package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.QsLdJgr;

import javax.servlet.http.HttpServletRequest;

public interface QsLdJgrService {

	PageObject<QsLdJgr> findObject(String dBname, Integer ldLxId, Integer pageCurrent, Integer pageSize);

	int saveObject(String dBname, QsLdJgr qsLdJgr);

	int deleteObjectById(String dBname, Integer...ids);

	int updateObject(String dBname, QsLdJgr qsLdJgr);

	List<QsLdJgr> findAllByRwType(String dBname, Integer ldLxId);

	void importObjects(String dBname, List<QsLdJgr> list);

	List<QsLdJgr> findObjectByldRwId(String dBname, Integer ldRwId);

	void doQsLdJg(String dBname, List<QsLdJgr> list, Integer appid,Integer tjid);

	void doimplements(String dBname, Integer appid, String msg, HttpServletRequest request);

	void deleteObjectByRwId(String dBname, Integer... ldRwid);

	void doQsLdJgByJgId(String dBname, Integer appid, Integer ldJgId);


}
