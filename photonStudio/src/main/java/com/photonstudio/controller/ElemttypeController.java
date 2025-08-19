package com.photonstudio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Elemttype;
import com.photonstudio.service.ElemttypeService;

@RestController
@RequestMapping("/zsqy/elemttype")
public class ElemttypeController {
	@Autowired
	private ElemttypeService elemttypeService;
	@RequestMapping("/{dBname}/findObject")
	public SysResult findObject(@PathVariable String dBname,
						Integer pageCurrent,Integer pageSize) {
		try {
			PageObject<Elemttype>pageObject=elemttypeService.findObject(dBname,pageCurrent,pageSize);
			return SysResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
	}
	@RequestMapping("/{dBname}/save")
	public SysResult saveObject(@PathVariable String dBname,Elemttype elemttype) {
		int rows=elemttypeService.saveObject(dBname,elemttype);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败");
	}
	@RequestMapping("/{dBname}/delete")
	public SysResult deleteObject(@PathVariable String dBname,Integer...ids) {
		int rows=elemttypeService.deleteObjectById(dBname,ids);
		if(rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者记录已经不存在");
	}
	@RequestMapping("/{dBname}/update")
	public SysResult updateObject(@PathVariable String dBname,Elemttype elemttype) {
		int rows=elemttypeService.updateObject(dBname,elemttype);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "更新失败");
	}
	@RequestMapping("/{dBname}/findAll")
	public SysResult findAll(@PathVariable String dBname) {
		List<Elemttype> list=elemttypeService.findAll(dBname);
		return SysResult.oK(list);
	}
	
	@RequestMapping("/{dBname}/findObjectByTypeid")
	public SysResult findObjectByTypeid(@PathVariable String dBname,Integer typeid) {
		List<Elemttype> list=elemttypeService.findObjectByTypeid(dBname,typeid);
		return SysResult.oK(list);
	}
	
	@RequestMapping("/{dBname}/findObjectById")
	public SysResult findObjectById(@PathVariable String dBname,Integer elemttypeid) {
		try {
			Elemttype elemttype=elemttypeService.findObjectById(dBname,elemttypeid);
			return SysResult.oK(elemttype);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
	}
}
