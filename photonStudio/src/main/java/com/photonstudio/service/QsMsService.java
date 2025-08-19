package com.photonstudio.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Qsmsdz;
import com.photonstudio.pojo.Qsmsjblog;
import com.photonstudio.pojo.Qsmsjbtime;
import com.photonstudio.pojo.Qsmsjg;
import com.photonstudio.pojo.Qsmsrw;

public interface QsMsService {

	List<Qsmsrw> findMsRw(String dBname, Integer drtypeid);

	void insertMsRw(String dBname, Qsmsrw qsmsrw);

	void updateMsRw(String dBname, Qsmsrw qsmsrw);

	void deleteMsRw(String dBname, Integer id);

	PageObject<Qsmsdz> findMsDz(String dBname, Integer rwid,Integer pageCurrent,Integer pageSize);

	void insertMsDz(String dBname, Qsmsdz qsmsdz);

	void updateMsDz(String dBname, Qsmsdz qsmsdz,Integer appid);

	void deleteMsDz(String dBname, Integer id);

	PageObject<Qsmsjg> findMsJg(String dBname, Integer dzid,Integer pageCurrent,Integer pageSize);

	void insertMsJg(String dBname,Integer dzid,Integer[] regids,String value);

	void updateMsJg(String dBname, Qsmsjg qsmsjg);

	void deleteMsJg(String dBname, Integer[] id);

	List<Qsmsjg> findQsmsjg(String dBname, Integer dzid);

	void insertQsmsjg(String dBname, Qsmsjg qsmsjg);

	Map<String, List<Qsmsrw>> findAllMsRw(String dBname);

	void insertMsJb(String dBname, String[] ymds, Integer[] rwids,Integer appid);

	Map<String, List<Qsmsrw>> findAllMsJb(String dBname);

	List<Qsmsjbtime> findQsMsJbtimeAfNow(String dBname);

	PageObject<Qsmsjblog> findMsJbLog(String dBname,Date startTime,Date endTime, Integer pageCurrent, Integer pageSize);

}
