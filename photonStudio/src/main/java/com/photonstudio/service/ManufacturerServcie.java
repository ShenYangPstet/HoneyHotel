package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Manufacturer;

public interface ManufacturerServcie {

	PageObject<Manufacturer> findObject(String dBname, String manufactorname, Integer pageCurrent, Integer pageSize);

	List<Manufacturer> findAll(String dBname);

	int saveObject(String dBname, Manufacturer manufacturer);

	int deleteObjectById(String dBname, Integer[] ids);

	int updateObject(String dBname, Manufacturer manufacturer);

}
