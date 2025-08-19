package com.photonstudio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Subinfo;
import com.photonstudio.service.SubinfoService;

@RestController
@RequestMapping("/zsqy/subinfo")
public class SubinfoController {
	@Autowired
	private SubinfoService subinfoService;
	@RequestMapping("/{dBname}/findObject")
	public SysResult findObject(@PathVariable String dBname,
				Integer subid,Integer pageCurrent,Integer pageSize) {
		PageObject<Subinfo>pageObject =new PageObject<>();
		try {
			pageObject=subinfoService.findObject(dBname,subid,pageCurrent,pageSize);
			return SysResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	@RequestMapping("/{dBname}/findAll")
	public SysResult findAll(@PathVariable String dBname,Integer subid) {
		try {
			List<Subinfo>list=subinfoService.findAll(dBname,subid);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	@RequestMapping("/{dBname}/save")
	public SysResult saveObject(@PathVariable String dBname,Subinfo subinfo) {
		int rows=subinfoService.saveObject(dBname,subinfo);
		if (rows==1) {
			return SysResult.oK();		
			}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/{dBname}/delete")
	public SysResult deleteObject(@PathVariable String dBname,Integer...ids) {
		for(Integer id : ids)
		{
			System.out.println("id==="+id);
		}
		int rows =subinfoService.deleteObjectById(dBname,ids);
		if (rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者记录已经不存在");
	}
	@RequestMapping("/{dBname}/update")
	public SysResult updateObject(@PathVariable String dBname,Subinfo subinfo) {
		int rows=subinfoService.updateObject(dBname,subinfo);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "更新失败");
	}
		
}
