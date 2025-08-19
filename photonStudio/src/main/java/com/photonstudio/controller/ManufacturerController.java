package com.photonstudio.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Manufacturer;
import com.photonstudio.service.ManufacturerServcie;

@RestController
@RequestMapping("/zsqy/manufacturer/{dBname}")
public class ManufacturerController {
	@Autowired
	private ManufacturerServcie manufacturerServcie;
	
	@RequestMapping("/findObject")
	public SysResult findObject(@PathVariable String dBname,String manufactorname,Integer pageCurrent,Integer pageSize) {
		PageObject<Manufacturer> pageObject=new PageObject<>();
		try {
			pageObject=manufacturerServcie.findObject(dBname,manufactorname,pageCurrent,pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		return SysResult.oK(pageObject);
}
	@RequestMapping("/findAll")
	public SysResult findAll(@PathVariable String dBname) {
		List<Manufacturer>list=new ArrayList<>();
		try {
			list=manufacturerServcie.findAll(dBname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		return SysResult.oK(list);
	}
	@RequestMapping("/save")
	public SysResult save(@PathVariable String dBname,Manufacturer manufacturer) {
		int rows=manufacturerServcie.saveObject(dBname,manufacturer);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/delete")
	public SysResult delete(@PathVariable String dBname,Integer... ids) {
		int rows= manufacturerServcie.deleteObjectById(dBname,ids);
		if(rows==ids.length) {
			
			return SysResult.oK("删除了"+rows+"条信息");
		}
		return SysResult.build(50009, "删除失败，或者已经信息不存在");
	}
	@RequestMapping("/update")
	public SysResult update(@PathVariable String dBname,Manufacturer manufacturer) {
		int rows=manufacturerServcie.updateObject(dBname,manufacturer);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "更新失败");
	}
	
}
