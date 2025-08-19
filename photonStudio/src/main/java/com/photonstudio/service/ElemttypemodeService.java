package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Elemttypemode;

public interface ElemttypemodeService {

	PageObject<Elemttypemode> findObject(String dBname, Integer elemttypeid, Integer pageCurrent, Integer pageSize);

	int saveObject(String dBname, Elemttypemode elemttypemode);

	int deleteObjectById(String dBname, Integer...ids);

	int updateObject(String dBname, Elemttypemode elemttypemode);

	List<List<Elemttypemode>> findElemttypemodeById(String dBname, Integer[] ids);

	void synchroElemt(String dBname);

}
