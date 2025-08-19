package com.photonstudio.service;

import java.util.*;

import com.photonstudio.pojo.DataInfo;
import com.photonstudio.pojo.EnergyQeury;
import com.photonstudio.pojo.Energyinfo;

public interface QueryAllenergyService {

	List<List<EnergyQeury>> queryAllenergy(String dBname, List<Energyinfo> energytypeinfoList);

	List<EnergyQeury> findSumenergyBytype(String dBname, List<Energyinfo> energytypeinfoList, String type, Date date);

	List<List<DataInfo>> findOneEnergyInfo(String dBname, String tagname, Date date1, Date date2);

	List<Energyinfo> queryAllenergyInfo(String dBname, int type);

	List<EnergyQeury> findSubEnergyInfoByType(String dBname, String tagname, String regname, String type, Date date);

	Energyinfo findObjectById(String dBname, Integer id);

	Energyinfo queryAppTotalenergyInfo(String dBname, int energytypeid, int isapptotal);

	Map<String, String> queryAppTotalenergy(String dBname, Energyinfo energyinfo);

	List<EnergyQeury> findzsEnergyBytype(String dBname, Energyinfo energyinfo, String type, Date date);

	List<EnergyQeury> findRegEnergyInfoByType(String dBname, String tagname, String regname, String type, Date date);

	List<List<EnergyQeury>> queryAllenergyNew(String dBname, List<Energyinfo> energytypeinfoList);

	List<EnergyQeury> findSumenergyBytypeNew(String dBname, List<Energyinfo> energytypeinfoList, String type,
			Date date);

	List<List<DataInfo>> findOneEnergyInfoNew(String dBname, String tagname, Date date1, Date date2);

	List<EnergyQeury> findzsEnergyBytypeNew(String dBname, Energyinfo energyinfo, String type, Date date);

	List<EnergyQeury> findSumenergyBytypeMom(String dBname, List<Energyinfo> energytypeinfoList, String type,
                                                    Date date,Date date2,Integer momType);

}
