package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Energyinfo;

public interface EnergyinfoService {

	PageObject<Energyinfo> findObject(String dBname, Integer pageCurrent, Integer pageSize);

	int saveObject(String dBname, Energyinfo energyinfo);

	int deleteObjectById(String dBname, Integer... ids);

	int uptadeObject(String dBname, Energyinfo energyinfo);

	List<Energyinfo> findAllEnergyinfo(String dBname);

	boolean findCheckApptotal(String dBname);

	Energyinfo findEnergyinfoByid(String dBname, Integer id);

}
