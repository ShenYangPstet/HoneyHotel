package com.photonstudio.mapper;

import java.util.List;

import com.photonstudio.pojo.Manufacturer;

public interface ManufacturerMapper {

	int getRowCount(String dBname, String manufactorname);

	List<Manufacturer> findObject(String manufactorname, String dBname, Integer startIndex, Integer pageSize);

	int insertObject(String dBname, Manufacturer manufacturer);

	int deleteObjectById(String dBname, Integer[] ids);

	int updateObject(String dBname, Manufacturer manufacturer);
	
}
