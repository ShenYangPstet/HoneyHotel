package com.photonstudio.service;


import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Elemtmodle;

public interface ElemtmodleService {

	PageObject<Elemtmodle> findObject(String dBname, Integer pageCurrent, Integer pageSize);

	int saveObject(String dBname, Elemtmodle elemtmodle);

	int deleteObjectById(String dBname, Integer...ids);

	int updateObject(String dBname, Elemtmodle elemtmodle);

	List<Elemtmodle> findAll(String dBname);

	List<Elemtmodle> findAllTypeMode(String dBname,String elemttypename);

}
