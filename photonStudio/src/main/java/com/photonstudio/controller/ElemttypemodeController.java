package com.photonstudio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Elemttypemode;
import com.photonstudio.service.ElemttypemodeService;
//基于elemttypemode 元素模板表
@RestController
@RequestMapping("/zsqy/elemttypemode")
public class ElemttypemodeController {
	@Autowired
	private ElemttypemodeService elemttypemodeService;
	//分页查询
	@RequestMapping("/{dBname}/findObject")
	public SysResult findObject(@PathVariable String dBname,Integer elemttypeid,
			               Integer pageCurrent,Integer pageSize) {
		try {
			PageObject<Elemttypemode>pageObject= elemttypemodeService
					.findObject(dBname,elemttypeid,pageCurrent,pageSize);
			return SysResult.oK(pageObject);
		} catch (Exception e) {
			
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		
	}
	//保存
	@RequestMapping("/{dBname}/save")
	public SysResult saveObject(@PathVariable String dBname,Elemttypemode elemttypemode) {
		int rows=elemttypemodeService.saveObject(dBname,elemttypemode);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
		
	}
	//删除
	@RequestMapping("/{dBname}/delete")
	public SysResult deleteObject(@PathVariable String dBname,Integer...ids) {
		int rows=elemttypemodeService.deleteObjectById(dBname,ids);
		if(rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者数据已经不存在");
	}
	//更新
	@RequestMapping("/{dBname}/update")
	public SysResult updateObject(@PathVariable String dBname,Elemttypemode elemttypemode) {
		int rows=elemttypemodeService.updateObject(dBname,elemttypemode);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "更新失败");
	}
	
	@RequestMapping("/{dBname}/findByIds")
	public SysResult findElemttypemodeById(@PathVariable String dBname,Integer...ids) {
		try {
			List<List<Elemttypemode>> elemttypemodeList=elemttypemodeService.findElemttypemodeById(dBname,ids);
			return SysResult.oK(elemttypemodeList);
		}catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "根据id查询元素模板查询失败");
		}
	}
	
	//同步工具栏
	@RequestMapping("/{dBname}/synchroElemt")
	public SysResult synchroElemt(@PathVariable String dBname) {
		try {
			System.out.println("start synchroElemt---"+dBname);
			elemttypemodeService.synchroElemt(dBname);
			return SysResult.oK();
		}catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "同步元素失败");
		}
	}
}
