package com.photonstudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Operationtime;
import com.photonstudio.service.OperationtimeService;

@RestController
@RequestMapping("/zsqy/operationtime/{dBname}")
public class OperationtimeController {
	@Autowired
	private OperationtimeService operationtimeService;
	@RequestMapping("/findObject")
	private SysResult findObject(@PathVariable String dBname,Integer drid,
			                 Integer pageCurrent,Integer pageSize) {
		PageObject<Operationtime> pageObject=new PageObject<>();
		try {
			pageObject=operationtimeService.findObject(dBname,drid,pageCurrent,pageSize);
			
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		return SysResult.oK(pageObject);
	}
	@RequestMapping("/save")
	public SysResult saveObject(@PathVariable String dBname,Operationtime operationtime) {
		//System.out.println(operationtime.toString());
		operationtime.setStatus(1);
		int rows=operationtimeService.saveObject(dBname,operationtime);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/delete")
	public SysResult deleteObject(@PathVariable String dBname,Integer...ids) {
		int rows=operationtimeService.deleteObjectById(dBname,ids);
		if(rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者有记录已经不存在");
	}
	@RequestMapping("/update")
	public SysResult updateObject(@PathVariable String dBname,Operationtime operationtime) {
		int rows=operationtimeService.updateObject(dBname,operationtime);
		if(rows==1) {
			return SysResult.oK();		
			}
		return SysResult.build(50009, "更新失败");
	}
}
