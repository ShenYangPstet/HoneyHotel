package com.photonstudio.controller;

import java.util.List;

import com.photonstudio.common.annotation.OperationLogAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Energyinfo;
import com.photonstudio.service.EnergyinfoService;

@RestController
@RequestMapping("/zsqy/energyinfo/{dBname}")
public class EnergyinfoController {
	@Autowired
	private EnergyinfoService energyinfoService;
	@RequestMapping("/findObject")
	public SysResult findObject(@PathVariable String dBname,
							Integer pageCurrent,Integer pageSize) {
		PageObject<Energyinfo>pageObject=new PageObject<>();
		try {
			pageObject=energyinfoService.findObject(dBname,pageCurrent,pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return SysResult.oK(pageObject);
	}
	
	@RequestMapping("/findAllEnergyinfo")
	public SysResult findAllEnergyinfo(@PathVariable String dBname) {
		try {
			List<Energyinfo> energyinfoList=energyinfoService.findAllEnergyinfo(dBname);
			return SysResult.oK(energyinfoList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	@RequestMapping("/findEnergyinfoByid")
	public SysResult findEnergyinfoByid(@PathVariable String dBname,Integer id) {
		try {
			Energyinfo energyinfo=energyinfoService.findEnergyinfoByid(dBname,id);
			return SysResult.oK(energyinfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	
	@RequestMapping("/save")
	@OperationLogAnnotation(operModul = "能耗管理",operType = "能耗配置-新增",Energy = "新增")
	public SysResult saveObject(@PathVariable String dBname,Energyinfo energyinfo) {
		int rows=energyinfoService.saveObject(dBname,energyinfo);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/delete")
	@OperationLogAnnotation(operModul = "能耗管理",operType = "能耗配置-删除",Energy = "删除")
	public SysResult deleteObject(@PathVariable String dBname,Integer...ids) {
		int rows=energyinfoService.deleteObjectById(dBname,ids);
		if(rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者有信息已经不存在");
	}
	@RequestMapping("/update")
	@OperationLogAnnotation(operModul = "能耗管理",operType = "能耗配置-修改",Energy = "修改")
	public SysResult updateObject(@PathVariable String dBname,Energyinfo energyinfo) {
		int rows=energyinfoService.uptadeObject(dBname,energyinfo);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "更新失败");
	}
	@RequestMapping("/check")//判断是否有总能耗配置
	public SysResult  findCheckApptotal(@PathVariable String dBname) {
		boolean flag =energyinfoService.findCheckApptotal(dBname);
		return SysResult.oK(flag);
	}
}
