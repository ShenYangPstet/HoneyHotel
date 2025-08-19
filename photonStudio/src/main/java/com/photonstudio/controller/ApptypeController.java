package com.photonstudio.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Apptype;
import com.photonstudio.service.ApptypeService;

@RestController
@RequestMapping("/zsqy/apptype")
public class ApptypeController {
	@Autowired
	private ApptypeService apptypeService;
	@RequestMapping("/findAll")
	public SysResult findAll() {
		List<Apptype> list=new ArrayList<>();
		try {
			list=apptypeService.findAll();
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	@RequestMapping("/findObject")
	public SysResult findObject(Integer pageCurrent,Integer pageSize) {
		PageObject<Apptype>pageObject=new PageObject<>();
		try {
			pageObject=apptypeService.findObject(pageCurrent,pageSize);
			return SysResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	@RequestMapping("/save")
	public SysResult saveObject(Apptype apptype) {
		int rows=apptypeService.saveObject(apptype);
		if(rows==1) {
		return SysResult.oK();
		}
		return SysResult.build(50009, "失败");
	}
	@RequestMapping("/delete")
	public SysResult deleteObjectById(Integer...ids) {
		int rows=apptypeService.deleteObjectById(ids);
		if(rows==ids.length) {
			return SysResult.oK();
			}
		return SysResult.build(50009, "删除失败或者数据已经不存在");
	}
	@RequestMapping("/update")
	public SysResult updateObjectById(Apptype apptype) {
		int rows=apptypeService.updateObject(apptype);
		if(rows==1) {
			return SysResult.oK();
			}
		return SysResult.build(50009, "失败");
	}
}
