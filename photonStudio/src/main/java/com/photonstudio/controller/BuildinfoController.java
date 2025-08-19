package com.photonstudio.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.photonstudio.common.ExcelUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Buildinfo;
import com.photonstudio.service.BuildinfoService;


@RestController
@RequestMapping("/zsqy/buildinfo")
public class BuildinfoController {
	@Autowired
  private BuildinfoService buildinfoService;
	//查询建筑信息
	@RequestMapping("/{dBname}/findObject")
	public SysResult findObjectByname(@PathVariable("dBname") String dBname,String buildname,
			                          Integer pageCurrent,Integer pageSize) {
		PageObject<Buildinfo> pageObject=new PageObject<>();
		
		try {
			pageObject=buildinfoService.findObjectByName(buildname, dBname,pageCurrent, pageSize);
			return SysResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
		
		
	}
	//增加
	@RequestMapping("/{dBname}/saveObject")
	public SysResult saveObject(@PathVariable("dBname") String dBname,Buildinfo buildinfo) {
		
		int rows=buildinfoService.saveObject(dBname,buildinfo);
		if (rows==1) {
			
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	//删除
	@RequestMapping("/{dBname}/deleteObject")
	public SysResult  deleteObject(@PathVariable(value = "dBname") String dBname,Integer...ids) {
		int rows= buildinfoService.deleteObjectById(dBname,ids);
		if(rows==ids.length) {
			
			return SysResult.oK("删除了"+rows+"条信息");
		}
		return SysResult.build(50009, "删除失败，或者信息不存在");
	}
	//修改
	@RequestMapping("/{dBname}/updateObject")
	public SysResult updateObject(@PathVariable(value = "dBname") String dBname,Buildinfo buildinfo) {
		
		int rows=buildinfoService.updateObject(dBname,buildinfo);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "更新失败");
	}
	//查询所有
		@RequestMapping("/{dBname}/findAll")
	public SysResult findAll(@PathVariable("dBname") String dBname) {
		List<Buildinfo>list=new ArrayList<>(); 
		try {
			list=buildinfoService.findAll(dBname);
			return SysResult.oK(list);
		} catch (Exception e) {
			
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
	}
	
	@RequestMapping("/{dBname}/export")
	public void exportExcel(@PathVariable String dBname,HttpServletResponse response) {
		List<Buildinfo>list=new ArrayList<>(); 
		try {
			list=buildinfoService.findAll(dBname);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		ExcelUtil.exportExcel(list, "建筑信息模板", "建筑模板", Buildinfo.class, dBname+"-建筑模板.xls", response);
	}
	
	@RequestMapping("/{dBname}/import")
	public SysResult importExcel(@PathVariable String dBname,MultipartFile file) {
		List<Buildinfo> list =ExcelUtil.importExcel(file, 1, 1, Buildinfo.class);
		try {
			buildinfoService.importObjects(dBname, list);
		} catch (Exception e) {
			
			e.printStackTrace();
			return SysResult.build(50009, "保存失败");
		}
		return SysResult.oK();
	}
}
