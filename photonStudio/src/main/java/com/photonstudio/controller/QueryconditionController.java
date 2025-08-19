package com.photonstudio.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Querycondition;
import com.photonstudio.service.QueryconditionService;

@RestController
@RequestMapping("/zsqy/querycondition/{dBname}")
public class QueryconditionController {
	@Autowired
	private QueryconditionService queryconditionService;
	@RequestMapping("/findObject")
	public SysResult findObject(@PathVariable String dBname) {
		List<Querycondition>list=new ArrayList<>();
		try {
			list=queryconditionService.findObject(dBname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		return SysResult.oK(list);
	}
	@RequestMapping("/save")
	public SysResult saveObject(@PathVariable String dBname, Querycondition querycondition) {
		int rows=queryconditionService.saveObject(dBname,querycondition);
		if(rows==1) {
			return SysResult.oK();		
			}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/delete")
	public SysResult deleteObjectByid(@PathVariable String dBname,Integer id) {
		int rows=queryconditionService.deleteObjectByid(dBname,id);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者数据已经不存在");
	}
	@RequestMapping("/update")
	public SysResult updateObject(@PathVariable String dBname, Querycondition querycondition) {
		int rows=queryconditionService.updateObject(dBname,querycondition);
		if(rows==1) {
			return SysResult.oK();		
			}
		return SysResult.build(50009, "更新失败");
	}
}
