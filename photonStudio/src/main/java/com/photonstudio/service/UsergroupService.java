package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Usergroup;

public interface UsergroupService {

	List<Usergroup> findAll(String dBname);

	PageObject<Usergroup> findObject(String dBname, Integer pageCurrent, Integer pageSize);

	int updateObject(String dBname, Usergroup usergroup);

	int deleteObject(String dBname, Integer...ids);

	int saveObject(String dBname, Usergroup usergroup);

	Usergroup findObjectById(String dBname, Integer id);

}
