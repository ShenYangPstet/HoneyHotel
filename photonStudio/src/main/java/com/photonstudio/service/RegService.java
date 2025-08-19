package com.photonstudio.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.photonstudio.pojo.EcharsObject;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Qstag;
import com.photonstudio.pojo.Reg;

import cn.afterturn.easypoi.handler.inter.IExcelExportServer;

public interface RegService extends IExcelExportServer{

	PageObject<Reg> findObject(String dBname, Integer drId, Integer pageCurrent, Integer pageSize);

	int saveObject(String dBname, Reg reg);

	int deleteObjectById(String dBname, Integer[] ids);

	int updateObject(String dBname, Reg reg);

	List<Reg> findAllByDrid(String dBname, Integer dr_Id, String isenergy);

	void importObjects(String dBname, List<Reg> list);

	List<Qstag> findQstag(String dBname);
	
	List<String> findTagname(String dBname, Integer drid);

	List<Reg> findRegByDrid(String dBname, Integer drid, String isenergy);

	List<Reg> findRegByListShowLevel(String dBname, Integer drid, String...regListShowLevels);

	List<Reg> findRegById(String dBname, Integer[] ids);
	
	int deleteObjectByDrids(String dBname,Integer[] drids);

	List<Reg> findAllByDrtypeid(String dBname, Integer drtypeid, String tagName);

	List<Qstag> exportQstag(String dBname);

	String findTagValueByRegId(String dBname, Integer regId);

	int updateRegValue(String dBname, Integer regId, String tagvaule);

	Integer getCountByShowTypeAndState(String dBname, String showType, String state);

	int getExportRowCount(String dBname, Integer drtypeid, String tagName);

	int getRegCount(String dBname, Integer drid);

	PageObject<Reg> findRegByRegName(String dBname, String regName, String rw, Integer pageCurrent, Integer pageSize);

	List<Reg> findRegBydrid(String dBname, Integer[] ids);

	List<EcharsObject> findRegEChars(String dBname, Integer[] ids);

	List<JSONObject> findImageRegSub(String dBname, List<JSONObject> list);

	List<Reg> findRegByNameDrids(String dBname, String regName, Integer[] drIds);

	Map<String, String> findTagValueByNames(String dBname, List<String> newList);
}
