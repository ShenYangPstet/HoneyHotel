package com.photonstudio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Elemtmodle;
import com.photonstudio.service.ElemtmodleService;

@RestController
@RequestMapping("/zsqy/elemtmodle")
public class ElemtmodleController {
	
	@Autowired
	private ElemtmodleService elemtmodleService;
	
	@RequestMapping("/{dBname}/findObject")
	public SysResult findObject(@PathVariable String dBname,
						Integer pageCurrent,Integer pageSize) {
		try {
			PageObject<Elemtmodle>pageObject=elemtmodleService.findObject(dBname,pageCurrent,pageSize);
			return SysResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
	}
	
	@RequestMapping("/{dBname}/save")
	public SysResult saveObject(@PathVariable String dBname,Elemtmodle elemtmodle) {
		int rows=elemtmodleService.saveObject(dBname,elemtmodle);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败");
	}
	
	@RequestMapping("/{dBname}/delete")
	public SysResult deleteObject(@PathVariable String dBname,Integer...ids) {
		int rows=elemtmodleService.deleteObjectById(dBname,ids);
		if(rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者记录已经不存在");
	}
	
	@RequestMapping("/{dBname}/update")
	public SysResult updateObject(@PathVariable String dBname,Elemtmodle elemtmodle) {
		int rows=elemtmodleService.updateObject(dBname,elemtmodle);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "更新失败");
	}
	
	@RequestMapping("/{dBname}/findAll")
	public SysResult findAll(@PathVariable String dBname) {
		try
		{
			List<Elemtmodle> list=elemtmodleService.findAll(dBname);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	@RequestMapping("/{dBname}/findAllTypeMode")
	public SysResult findAllTypeMode(@PathVariable String dBname,String elemttypename) {
		try
		{
			List<Elemtmodle> list=elemtmodleService.findAllTypeMode(dBname,elemttypename);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}

}
