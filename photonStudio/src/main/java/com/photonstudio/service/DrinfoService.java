package com.photonstudio.service;

import com.github.pagehelper.PageInfo;
import com.photonstudio.dataupload.req.DeviceReq;
import com.photonstudio.dataupload.vo.DeviceVO;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.pojo.Drinfo;
import com.photonstudio.pojo.req.DeviceListDataReq;
import com.photonstudio.pojo.vo.DeviceListDataVO;

public interface DrinfoService {

	PageObject<Drinfo> findObject(String dBname, Integer drtypeid, String mdcode,Integer userid, Integer pageCurrent, Integer pageSize,String drname);

	int saveObject(String dBname, Drinfo drinfo);

	int deleteObjectById(String dBname, Integer...ids);

	int updateObject(String dBname, Drinfo drinfo);

	List<Drinfo> findAll(String dBname, Integer drtypeid, Integer userid,Integer floorId);

	int saveObjects(String dBname, List<Drinfo> list);

	void importObjects(String dBname, List<Drinfo> list);

	int getRowCount(String dBname, Integer drtypeid, String mdcode);

	int updateUser(String dBname, Integer userid, Integer... ids);

	List<Drinfo> findAllIsUser(String dBname, Integer drtypeid);

	Drinfo findDrByDrid(String dBname, Integer drid);

	int findObjectById(String dBname, Integer drid);

	List<Drinfo> findObjectByids(String dBname, String drname, Integer[] ids);
	
	List<Integer> findDridByUserid(String dBname,Integer userid);

	int removeUser(String dBname, Integer userid, Integer...ids);

    List<JSONObject> findDrByDrIds(String dBname,String drname, Integer[] drIds);


	PageObject<Drinfo>  findByListPage(Drinfo drinfo);

	DeviceListDataVO findDeviceList(DeviceListDataReq deviceListDataReq);

	PageInfo<DeviceVO> findDevicePage(DeviceReq deviceReq);
}
