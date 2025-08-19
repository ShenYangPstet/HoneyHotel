package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Regalarminfo;

import cn.afterturn.easypoi.handler.inter.IExcelExportServer;

public interface RegalarminfoService extends IExcelExportServer{

	PageObject<Regalarminfo> findObject(String dBname, Integer regId, Integer pageCurrent, Integer pageSize);

	List<Regalarminfo> findAllByRegId(String dBname, Integer regId);
	
	int saveObject(String dBname, Regalarminfo regalarminfo);

	int deleteObjectByIds(String dBname, Integer... ids);

	int deleteObjectByRegId(String dBname, Integer regId);

	int updateObject(String dBname, Regalarminfo regalarminfo);

	void importObjects(String dBname, List<Regalarminfo> list);

	int getRowCount(String dBname, Integer regId);



}
