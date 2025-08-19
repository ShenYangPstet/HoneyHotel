package com.photonstudio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Storeroom;
import com.photonstudio.service.StoreroomService;

@RestController
@RequestMapping("/zsqy/storeroom/{dBname}")
public class StoreroomController {
	@Autowired
	private StoreroomService storeroomService;
	@RequestMapping("/findObject")
	public SysResult findObject(@PathVariable String dBname,
			                   Integer pageCurrent,Integer pageSize) {
		PageObject<Storeroom>pageObject=new PageObject<>();
		try {
			pageObject=storeroomService.findObject(dBname,pageCurrent,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		return SysResult.oK(pageObject);
	}
	@RequestMapping("/findAll")
	public SysResult findAll(@PathVariable String dBname) {
		List<Storeroom>list=storeroomService.findAll(dBname);
		return SysResult.oK(list);
	}
	@RequestMapping("/save")
	public SysResult saveObject(@PathVariable String dBname,Storeroom storeroom) {
		int rows=storeroomService.saveObject(dBname,storeroom);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/delete")
	public SysResult deleteObject(@PathVariable String dBname,Integer...ids) {
		int rows=storeroomService.deleteObjectById(dBname,ids);
		if(rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者有记录已经不存在");
	}
	@RequestMapping("/update")
	public SysResult updateObject(@PathVariable String dBname,Storeroom storeroom) {
		int rows=storeroomService.updateObject(dBname,storeroom);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "更新失败");
	}
}
