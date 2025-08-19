package com.photonstudio.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Departmentpic;
import com.photonstudio.pojo.Pic;
import com.photonstudio.service.DepartmentpicService;

@RestController
@RequestMapping("/zsqy/departmentpic/{dBname}")
public class DepartmentpicController {
	@Autowired
	private DepartmentpicService departmentpicService;
	@RequestMapping("/findObject")
	public SysResult findObject(@PathVariable String dBname,Integer departmentid) {
		List<Departmentpic>list=departmentpicService.findObkect(dBname,departmentid);
		return SysResult.oK(list);
	}
	@RequestMapping("/save")
	public SysResult saveObject(@PathVariable String dBname,Integer departmentid,
								Integer...picids) {
		try
		{
			int rows=departmentpicService.saveObject(dBname,departmentid,picids);
			return SysResult.oK();
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "有信息保存失败");
		}
	}
	@RequestMapping("/delete")
	public SysResult deleteObjectById(@PathVariable String dBname,Integer...ids ) {
		int rows=departmentpicService.deleteObject(dBname,ids);
		if(rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者有信息已经不存在");
	}
	@RequestMapping("/findPic")
	public SysResult findPicByDepartmentid(@PathVariable String dBname,Integer departmentid) {
		List<Pic>list=new ArrayList<>();
		try {
			list=departmentpicService.findPicByDepartmentid(dBname,departmentid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		
		return SysResult.oK(list);
	}
}
