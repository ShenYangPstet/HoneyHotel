package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Elemttype;

public interface ElemttypeService {

	PageObject<Elemttype> findObject(String dBname, Integer pageCurrent, Integer pageSize);

	int saveObject(String dBname, Elemttype elemttype);

	int deleteObjectById(String dBname, Integer...ids);

	int updateObject(String dBname, Elemttype elemttype);

	List<Elemttype> findAll(String dBname);

	List<Elemttype> findObjectByTypeid(String dBname, Integer typeid);

	Elemttype findObjectById(String dBname, Integer elemttypeid);

}
