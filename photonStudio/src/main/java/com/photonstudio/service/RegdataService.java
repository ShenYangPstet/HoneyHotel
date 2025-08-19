package com.photonstudio.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.photonstudio.pojo.Regdata;

public interface RegdataService {

	List<Regdata> findObject(String dBname, String tagname, Date startTime, Date endTime);

	int saveObject(String dBname, String tagname, Regdata regdata);

	int deleteObjectById(String dBname, String tagname, Integer...ids);

	int updateObject(String dBname, String tagname, Regdata regdata);

	List<Map<String, List<Regdata>>> findEcharsByTagname(String dBname, String regname, Date startTime, Date endTime, String... tagname);

	List<Map<String, List<Regdata>>> findEcharsByDrid(String dBname,Integer drid ,Date startTime, Date endTime);

	List<Map<String, List<Regdata>>> findRegdataById(String dBname, Integer[] ids);

	Map<String, List<String>> findDridReport(String dBname, Integer drid, Integer times, Date startTime, Date endTime);

	Map<String, List<String>> findDridReportNew(String dBname, Integer drid, Integer times, Date startTime,
			Date endTime);

	List<Map<String, List<Regdata>>> findEcharsByTagnameNew(String dBname, String regname, Date startTime, Date endTime,
																							 String[] tagname);

	List<Integer> findMorenDrid(String dBname);

	Map<String, List<Regdata>> exportNew(String dBname, String regname, Date startTime, Date endTime,
			String[] tagname);

	List<JSONObject> findRegDataByRegs(String dBname, Date startTime, Date endTime, Integer[] regIds);

	Map<String, List<String>> findReportByRegIds(String dBname, Integer times, Date startTime, Date endTime, Integer[] regids);

	JSONObject findCurvetByRegIds(String dBname, Integer times, Date startTime, Date endTime, Integer[] regids);
}
