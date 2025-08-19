package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Storeroom;

public interface StoreroomService {

	PageObject<Storeroom> findObject(String dBname, Integer pageCurrent, Integer pageSize);

	int saveObject(String dBname, Storeroom storeroom);

	int deleteObjectById(String dBname, Integer... ids);

	int updateObject(String dBname, Storeroom storeroom);

	List<Storeroom> findAll(String dBname);

}
