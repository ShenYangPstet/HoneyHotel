package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Apptype;

public interface ApptypeService {

	List<Apptype> findAll();

	

	int saveObject(Apptype apptype);



	int deleteObjectById(Integer... ids);



	int updateObject(Apptype apptype);



	PageObject<Apptype> findObject(Integer pageCurrent, Integer pageSize);

}
