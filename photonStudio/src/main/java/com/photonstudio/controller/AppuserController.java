package com.photonstudio.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Appuser;
import com.photonstudio.service.AppuserService;

@RestController
@RequestMapping("/zsqy/appuser/{dBname}")
public class AppuserController {
	@Autowired
	private AppuserService appuserService;
	@RequestMapping("/findAll")
	public SysResult findAll(@PathVariable String dBname,String username) {
		List<Appuser>list=appuserService.findAll(dBname,username);
		return SysResult.oK(list);
	}
	@RequestMapping("/findObject")
	public SysResult findObject(@PathVariable String dBname,String username,Integer pageCurrent,Integer pageSize) {
		PageObject<Appuser>pageObject=new PageObject<>();
		try {
			pageObject=appuserService.findObject(dBname,username,pageCurrent,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return SysResult.oK(pageObject);
	}
	@RequestMapping("/save")
	public SysResult saveObject(@PathVariable String dBname,Appuser appuser) {
		int rows =appuserService.saveObject(dBname,appuser);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
		
	}
	@RequestMapping("/delete")
	public SysResult deleteObject(@PathVariable String dBname,Integer appid,Integer...ids) {
		int rows=appuserService.deleteObject(dBname,appid,ids);
		if(rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者有信息不存在");
	}
	@RequestMapping("/update")
	public SysResult updateObject(@PathVariable String dBname,Appuser appuser,Integer appid) {
		int rows=appuserService.updateObject(dBname,appuser,appid);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "更新失败");
	}
	
	@RequestMapping("/saveAppuser")
	public SysResult saveAppuser(@PathVariable String dBname,Appuser appuser,Integer appid,Date licensetime) {
		int rows =appuserService.saveAppuser(dBname,appuser,appid,licensetime);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
		
	}
}
