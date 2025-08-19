package com.photonstudio.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.InterfaceConfig;
import com.photonstudio.service.InterfaceConfigService;

@RestController
@RequestMapping("/zsqy/interfaceConfig")
public class InterfaceConfigController {
	@Autowired
	private InterfaceConfigService interfaceConfigService;
	@RequestMapping("/findObject")
	public SysResult findObject(String explain,Integer interfaceType,Integer pageCurrent,Integer pageSize) {
		PageObject<InterfaceConfig>pageObject=new PageObject<>();
		try {
			pageObject=interfaceConfigService.findObject(explain,interfaceType,pageCurrent,pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		return SysResult.oK(pageObject);
		
	}
	
	@RequestMapping("/findAll")
	public SysResult findAll(String explain,Integer interfaceType) {
		List<InterfaceConfig>list=new ArrayList<>();
		try {
			list=interfaceConfigService.findAll(explain,interfaceType);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		return SysResult.oK(list);
	}
	@RequestMapping("/save")
	public SysResult saveObject(InterfaceConfig interfaceConfig) {
		int row=interfaceConfigService.saveObject(interfaceConfig);
		if(row==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/delete")
	public SysResult deleteObject(Integer...ids) {
		int rows=interfaceConfigService.deleteObject(ids);
		if (rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者记录已经不存在");
		
	}
	@RequestMapping("/update")
	public SysResult updateObject(InterfaceConfig interfaceConfig){
	int rows=interfaceConfigService.updateObject(interfaceConfig);
	if(rows==1) {
		return SysResult.oK();
	}
	return SysResult.build(50009, "更新失败");
	}
}
