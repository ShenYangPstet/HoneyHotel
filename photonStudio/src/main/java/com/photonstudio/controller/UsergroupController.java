package com.photonstudio.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Usergroup;
import com.photonstudio.service.UsergroupService;

@RestController
@RequestMapping("/zsqy/usergroup")
public class UsergroupController {
	
	@Autowired
	private UsergroupService usergroupService;
	
	@RequestMapping("/{dBname}/findAll")
	public SysResult findAll(@PathVariable String dBname) {
		List<Usergroup> list=new ArrayList<>();
		try {
			list=usergroupService.findAll(dBname);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	@RequestMapping("/{dBname}/findObject")
	public SysResult findObject(@PathVariable String dBname,
								Integer pageCurrent,Integer pageSize) {
		PageObject<Usergroup> pageObject=new PageObject<>();
		try {
			pageObject=usergroupService.findObject(dBname,pageCurrent,pageSize);
			return SysResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	@RequestMapping("/{dBname}/findObjectById")
	public SysResult findObjectById(@PathVariable String dBname,Integer id) {
		Usergroup usergroup=usergroupService.findObjectById(dBname,id);
		return SysResult.oK(usergroup);
	}
	
	@RequestMapping("/{dBname}/save")
	public SysResult save(@PathVariable String dBname,Usergroup usergroup) {
		int rows=usergroupService.saveObject(dBname,usergroup);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/{dBname}/delete")
	public SysResult deleteObject(@PathVariable String dBname,Integer...ids) {
		int rows=usergroupService.deleteObject(dBname,ids);
		if (rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者信息以及不存在");
	}
	@RequestMapping("/{dBname}/update")
	public SysResult update(@PathVariable String dBname,Usergroup usergroup) {
		int rows=usergroupService.updateObject(dBname,usergroup);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "跟新失败");
	}

}
