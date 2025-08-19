package com.photonstudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Queryresultinfo;
import com.photonstudio.service.QueryresultinfoService;

@RestController
@RequestMapping("/zsqy/queryresultinfo/{dBname}")
public class QueryresultinfoController {
	@Autowired
	private QueryresultinfoService queryresultinfoService;
	@RequestMapping("/findObject")
	public SysResult findObject(@PathVariable String dBname,Integer resultId,
			Integer pageCurrent,Integer pageSize) {
		
		PageObject<Queryresultinfo>list=new PageObject<>();
		try {
			list=queryresultinfoService.findObject(dBname,resultId,pageCurrent,pageSize);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		return SysResult.oK(list);
	}
		
	@RequestMapping("/save")
	private SysResult save(@PathVariable String dBname,Queryresultinfo queryresultinfo) {
		int rows=queryresultinfoService.saveObject(dBname,queryresultinfo);
		if(rows==1) {
			return SysResult.oK();		
			}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/delete")
	public SysResult deleteObjectByid(@PathVariable String dBname,Integer id) {
		int rows=queryresultinfoService.deleteObjectByid(dBname,id);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者数据已经不存在");
	}
	@RequestMapping("/update")
	public SysResult updateObject(@PathVariable String dBname, Queryresultinfo queryresultinfo) {
		int rows=queryresultinfoService.updateObject(dBname,queryresultinfo);
		if(rows==1) {
			return SysResult.oK();		
			}
		return SysResult.build(50009, "更新失败");
	}
}
