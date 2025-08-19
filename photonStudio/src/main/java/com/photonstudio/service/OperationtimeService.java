package com.photonstudio.service;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Operationtime;

public interface OperationtimeService {

	PageObject<Operationtime> findObject(String dBname, Integer drid, Integer pageCurrent, Integer pageSize);

	int saveObject(String dBname, Operationtime operationtime);

	int deleteObjectById(String dBname, Integer...ids);

	int updateObject(String dBname, Operationtime operationtime);

}
