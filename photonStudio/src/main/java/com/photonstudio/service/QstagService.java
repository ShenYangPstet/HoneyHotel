package com.photonstudio.service;

import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Qstag;

import java.util.List;

public interface QstagService extends IExcelExportServer{

	PageObject<Qstag> findObject(String dBname, String tagname, Integer itemdrid, Integer pageCurrent,
			Integer pageSize);

	int saveObject(String dBname, Qstag qstag);

	int deleteObjectById(String dBname, Integer[] ids);

	int updateObject(String dBname, Qstag qstag);

	List<Qstag> findQstag(String dBname, String tagname, Integer itemdrid);

	void importObjects(String dBname, List<Qstag> list);

	int findExportCount(String dBname, String tagname, Integer itemdrid);

	int updateByTagName(String dBname, String msg);

    List<Qstag> findTagValueByTagName(String[] tagname);

	int fidnudge(String tagname);
}
