package com.photonstudio.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Queryresult;
import com.photonstudio.service.QueryresultService;

@RestController
@RequestMapping("/zsqy/queryresult/{dBname}")
public class QueryresultController {
	@Autowired
	private QueryresultService queryresultService;
	
	@RequestMapping("/findObject")
	public SysResult findObject(@PathVariable String dBname) {
		List<Queryresult>list=new ArrayList<>();
		try {
			list=queryresultService.findObject(dBname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		return SysResult.oK(list);
	}
	@RequestMapping("/save")
	public SysResult saveObject(@PathVariable String dBname, Queryresult queryresult) {
		int rows=queryresultService.saveObject(dBname,queryresult);
		if(rows==1) {
			return SysResult.oK();		
			}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/delete")
	public SysResult deleteObjectByid(@PathVariable String dBname,Integer id) {
		int rows=queryresultService.deleteObjectByid(dBname,id);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者数据已经不存在");
	}
	@RequestMapping("/update")
	public SysResult updateObject(@PathVariable String dBname, Queryresult queryresult) {
		int rows=queryresultService.updateObject(dBname,queryresult);
		if(rows==1) {
			return SysResult.oK();		
			}
		return SysResult.build(50009, "更新失败");
	}
}
