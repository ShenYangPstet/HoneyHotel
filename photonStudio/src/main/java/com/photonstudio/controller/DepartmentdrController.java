package com.photonstudio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Departmentdr;
import com.photonstudio.service.DepartmentdrService;

@RestController
@RequestMapping("/zsqy/departmentdr/{dBname}")
public class DepartmentdrController {
	@Autowired
	private DepartmentdrService departmentdrService;
	
	
	@RequestMapping("/findObject")
	public SysResult findObject(@PathVariable String dBname,Integer departmentid) {
		List<Departmentdr>list=departmentdrService.findObject(dBname, departmentid);
		return SysResult.oK(list);
	}
	@RequestMapping("/save")
	public SysResult saveObject(@PathVariable String dBname,Integer departmentid,Integer...drids) {
		
		int rows=departmentdrService.saveObject(dBname,departmentid,drids);
		if(rows==drids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/delete")
	public SysResult deleteObject(@PathVariable String dBname,
										Integer... ids) {
		int rows=departmentdrService.deleteObject(dBname,ids);
		if(rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者有信息不存在");
	}
}
