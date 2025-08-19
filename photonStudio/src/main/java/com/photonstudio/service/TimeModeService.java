package com.photonstudio.service;

import java.util.List;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.QsLdTgr;
import com.photonstudio.pojo.Timemode;
import com.photonstudio.pojo.Timemodeinfo;
import com.photonstudio.pojo.YmdJob;

public interface TimeModeService {

	PageObject<Timemode> findObject(String dBname, Integer pageCurrent, Integer pageSize);

	int saveObject(String dBname, Timemode timemode);

	int deleteObject(String dBname, Integer[] ids);

	int updateObject(String dBname, Timemode timemode);

	List<Timemodeinfo> findModeinfoByid(String dBname, Integer id);

	PageObject<Timemodeinfo> findObjectinfo(String dBname, Integer pageCurrent, Integer pageSize);

	int saveObjectinfo(String dBname, Timemodeinfo timemodeinfo);

	int updateObjectinfo(String dBname, Timemodeinfo timemodeinfo);

	int deleteObjectinfo(String dBname, Integer[] ids);

	List<Timemode> findAllMode(String dBname);

	List<QsLdTgr> findAllTg(String dBname);

	void saveTimemodejob(String dBname, String timemodes,Integer appid) throws Exception;

	List<YmdJob> findTimemodejob(String dBname, String yearMonth);

	List<Timemodeinfo> findModeinfoByTjIds(String dBname, Integer[] ids);

	List<Timemodeinfo> findObjectinfoByIds(String dBname, Integer... ids);



}
