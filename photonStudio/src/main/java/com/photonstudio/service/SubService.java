package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Sub;

public interface SubService {

	List<Sub> findAll(String dBname);

	int saveObject(String dBname, Sub sub);

	int deleteObjectById(String dBname, Integer... ids);

	int updateObject(String dBname, Sub sub);

	PageObject<Sub> findObject(String dBname, Integer pageCurrent, Integer pageSize);

}
