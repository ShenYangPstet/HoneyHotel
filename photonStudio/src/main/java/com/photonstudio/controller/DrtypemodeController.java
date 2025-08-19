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
import com.photonstudio.pojo.Drtypemode;
import com.photonstudio.service.DrtypemodeService;

@RestController
@RequestMapping("/zsqy/Drtypemode")
public class DrtypemodeController {
	@Autowired
	private DrtypemodeService drtypemodeService;
	@RequestMapping("/{dBname}/findObject")
	public SysResult findObject(@PathVariable("dBname") String dBname,
			     Integer drtypeid,Integer pageCurrent,Integer pageSize) {
		PageObject<Drtypemode> pageObject=new PageObject<>();
		try {
			pageObject=drtypemodeService.findObject(dBname,drtypeid,pageCurrent,pageSize);
			return SysResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	@RequestMapping("/{dBname}/findAll")
	public SysResult findAll(@PathVariable String dBname,Integer drtypeid) {
		List<Drtypemode>list=new ArrayList<>();
		try {
			list=drtypemodeService.findAllByDrtypeid(dBname,drtypeid);
			return SysResult.oK(list);
		} catch (Exception e) {
			
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	@RequestMapping("/{dBname}/findAllWrite")
	public SysResult findAllWrite(@PathVariable String dBname,Integer drtypeid) {
		List<Drtypemode>list=new ArrayList<>();
		try {
			list=drtypemodeService.findAllWriteByDrtypeid(dBname,drtypeid);
			return SysResult.oK(list);
		} catch (Exception e) {
			
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	@RequestMapping("/{dBname}/writeDrtypeMode")
	public SysResult writeDrtypeMode(@PathVariable String dBname,Integer drtypeid,Integer appid,String msg) {
		try {
			drtypemodeService.writeDrtypeMode(dBname,drtypeid,appid,msg);
			return SysResult.oK();
		} catch (Exception e) {
			
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	@RequestMapping("/{dBname}/save")
	public SysResult save(@PathVariable String dBname,Drtypemode drtypemode) {
		int rows=drtypemodeService.saveObject(dBname,drtypemode);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/{dBname}/update")
	public SysResult updateObject(@PathVariable String dBname,Drtypemode drtypemode) {
		int rows=drtypemodeService.updateObject(dBname,drtypemode);
		if(rows==1) {
			return SysResult.oK();		
			}
		return SysResult.build(50009, "更新失败");
	}
	@RequestMapping("/{dBname}/delete")
	public SysResult deleteObject(@PathVariable String dBname,Integer...ids) {
		int rows=drtypemodeService.deleteObjectById(dBname,ids);
		if (rows==ids.length) {
			return SysResult.oK("删除了"+rows+"条记录");
		}
		return SysResult.build(50009, "删除失败或记录已经不存在");
	}
	@RequestMapping("/{dBname}/export")
	public void exportExcel(@PathVariable String dBname,Integer drtypeid,HttpServletResponse response) {
		List<Drtypemode>list=new ArrayList<>();
		try {
			list=drtypemodeService.findAllByDrtypeid(dBname, drtypeid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		ExcelUtil.exportExcel(list, "设备类型模板", "类型模板", Drtypemode.class, dBname+"-设备类型模板.xls", response);
	}
	@RequestMapping("/{dBname}/import")
	public SysResult importExcel(@PathVariable String dBname,MultipartFile file) {
		List<Drtypemode>list =ExcelUtil.importExcel(file, 1, 1, Drtypemode.class);
		try {
			drtypemodeService.importObjects(dBname, list);
		} catch (Exception e) {
			
			e.printStackTrace();
			return SysResult.build(50009, "保存失败");
		}
		return SysResult.oK();
	}
}
