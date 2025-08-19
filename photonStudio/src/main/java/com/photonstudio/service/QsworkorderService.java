package com.photonstudio.service;

import java.util.Date;
import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.DrCheckLog;
import com.photonstudio.pojo.EcharsObject;
import com.photonstudio.pojo.Qsworkorder;

public interface QsworkorderService {

	PageObject<Qsworkorder> findObject(String dBname, Integer state, Integer pageCurrent, Integer pageSize);

	List<Qsworkorder> findObjectByNumber(String dBname, String number);

	int delete(String dBname, Integer[] id);

	int save(String dBname, Qsworkorder qsworkorder);

	int update(String dBname, Qsworkorder qsworkorder);

	List<EcharsObject> findECByState(String dBname);

	List<Qsworkorder> findAll(String dBname, Integer[] ids);

	PageObject<DrCheckLog> finddrcehcklog(String dBname, String checkPerson, Date checkDate, Integer pageCurrent,
			Integer pageSize);

    List<Qsworkorder> findAllworklist(String dBname, int state, String username, String workuser, Integer approvestate);
}
