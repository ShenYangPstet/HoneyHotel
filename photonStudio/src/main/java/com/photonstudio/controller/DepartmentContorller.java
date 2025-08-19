package com.photonstudio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Department;
import com.photonstudio.service.DepartmentService;

@RestController
@RequestMapping("/zsqy/department/{dBname}")
public class DepartmentContorller {
	@Autowired
	private DepartmentService departmentService;
	@RequestMapping("/findObjectByFuid")
	private SysResult findObjectByFuid(@PathVariable String dBname ,Integer fuid) {
		if (fuid==null) {
			fuid=0;
		}
		List<Department>list=departmentService.findObjectByFuid(dBname,fuid);
		return SysResult.oK(list);	
		}
	@RequestMapping("/findObject")
	public SysResult findObject(@PathVariable String dBname,Integer pageCurrent,
									Integer pageSize) {
		PageObject<Department>pageObject=new PageObject<>();
		try {
			pageObject=departmentService.findObject(dBname,pageCurrent,pageSize);
		} catch (Exception e) {
		e.printStackTrace();
		throw new ServiceException("查询失败");
		}
		return SysResult.oK(pageObject);
	}
	@RequestMapping("/findAll")
	public SysResult findAll(@PathVariable String dBname) {
		try {
			List<Department>list=departmentService.findAll(dBname);
			return SysResult.oK(list);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SysResult.build(50009, "查询失败");
	}
	@RequestMapping("/save")
	public SysResult saveObject(@PathVariable String dBname,Department department) {
		if(department.getFuid()==null)department.setFuid(0);
		int rows=departmentService.insertObject(dBname,department);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/delete")
	public SysResult deleteObjectById(@PathVariable String dBname,Integer departmentid) {
		int rows=departmentService.deleteObjectById(dBname,departmentid);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者数据以及不存在");
	}
	@RequestMapping("/update")
	public SysResult updateObject(@PathVariable String dBname,Department department) {
		int rows=departmentService.updateObject(dBname,department);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
}
