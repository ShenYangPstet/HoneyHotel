package com.photonstudio.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Alarmtype;
import com.photonstudio.pojo.UserAlarmlevel;
import com.photonstudio.service.AlarmtypeService;

@RestController
@RequestMapping("/zsqy/alarmtype")
public class AlarmtypeController {
	@Autowired
	private AlarmtypeService alarmtypeService;
	@RequestMapping("/{dBname}/findAll")
	public SysResult findAll(@PathVariable String dBname) {
		List<Alarmtype>list =new ArrayList<>();
		try {
			list=alarmtypeService.findAll(dBname);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	@RequestMapping("/{dBname}/findObject")
	public SysResult findObject(@PathVariable String dBname,
								Integer pageCurrent,Integer pageSize) {
		PageObject<Alarmtype> pageObject=new PageObject<>();
		try {
			pageObject=alarmtypeService.findObject(dBname,pageCurrent,pageSize);
			return SysResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	@RequestMapping("/{dBname}/save")
	public SysResult save(@PathVariable String dBname,Alarmtype alarmtype) {
		int rows=alarmtypeService.saveObject(dBname,alarmtype);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/{dBname}/delete")
	public SysResult deleteObject(@PathVariable String dBname,Integer...ids) {
		int rows=alarmtypeService.deleteObject(dBname,ids);
		if (rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者信息以及不存在");
	}
	@RequestMapping("/{dBname}/update")
	public SysResult update(@PathVariable String dBname,Alarmtype alarmtype) {
		int rows=alarmtypeService.updateObject(dBname,alarmtype);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "跟新失败");
	}
	
	@RequestMapping("/{dBname}/findUserAlarm")
	public SysResult findUserAlarm(@PathVariable String dBname,Integer userid) {
		try
		{
			List<UserAlarmlevel> userAlarmList=alarmtypeService.findUserAlarm(dBname,userid);
			return SysResult.oK(userAlarmList);
			
		}catch (Exception e) {
			// TODO: handle exception
			return SysResult.build(50009, "查询失败");
		}
	}
	
	@RequestMapping("/{dBname}/updateUserAlarm")
	public SysResult updateUserAlarm(@PathVariable String dBname,Integer userid,Integer...ids) {
		try
		{
			int rows=alarmtypeService.updateUserAlarm(dBname,userid,ids);
			if (rows > 0) {
				return SysResult.oK();
			}
			return SysResult.build(50009, "更新失败");
			
		}catch (Exception e) {
			// TODO: handle exception
			return SysResult.build(50009, "更新失败");
		}
	}
}
