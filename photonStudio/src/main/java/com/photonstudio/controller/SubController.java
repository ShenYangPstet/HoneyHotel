package com.photonstudio.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Sub;
import com.photonstudio.service.SubService;

@RestController
@RequestMapping("/zsqy/sub")
public class SubController {
	@Autowired
	private SubService subService;
	@RequestMapping("/{dBname}/findAll")
	public SysResult findAll(@PathVariable String dBname) {
		List<Sub> list=new ArrayList<>();
		try {
			list=subService.findAll(dBname);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	@RequestMapping("/{dBname}/findObject")
	public SysResult findObject(@PathVariable String dBname,Integer pageCurrent,
									Integer pageSize) {
		PageObject<Sub> pageObject=new PageObject<>();
		try {
			pageObject=subService.findObject(dBname,pageCurrent,pageSize);
			return SysResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		
	}
	@RequestMapping("/{dBname}/save")
	public SysResult saveObject(@PathVariable String dBname,Sub sub) {
		int rows=subService.saveObject(dBname,sub);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/{dBname}/delete")
	public SysResult deleteObject(@PathVariable String dBname,Integer...ids) {
		int rows=subService.deleteObjectById(dBname,ids);
		if (rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者记录已经不存在");
	}
	@RequestMapping("/{dBname}/update")
	public SysResult updateObject(@PathVariable String dBname,Sub sub) {
		int rows=subService.updateObject(dBname,sub);
		if (rows==1) {
			return SysResult.oK();	
		}
		return SysResult.build(50009, "更新失败");
	}
}
