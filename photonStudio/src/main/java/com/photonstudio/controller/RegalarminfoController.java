package com.photonstudio.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.photonstudio.pojo.Regalarminfo;
import com.photonstudio.service.RegalarminfoService;

@RestController
@RequestMapping("/zsqy/regalarminfo/{dBname}")
public class RegalarminfoController {
	@Autowired
	private RegalarminfoService regalarminfoService;
	@RequestMapping("/findObject")
		public SysResult findObject(@PathVariable String dBname,Integer regId,
								Integer pageCurrent,Integer pageSize) {
		PageObject<Regalarminfo> pageObject=new PageObject<>();
		try {
			pageObject=regalarminfoService.findObject(dBname,regId,pageCurrent,pageSize);
			return SysResult.oK(pageObject);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		
	}
	@RequestMapping("/findAll")
	public SysResult findAllByRegId(@PathVariable String dBname,Integer regId) {
		List<Regalarminfo>list=new ArrayList<>();
		try {
			list=regalarminfoService.findAllByRegId(dBname,regId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return SysResult.oK(list);
	}
	@RequestMapping("/save")
	public SysResult saveObject(@PathVariable String dBname,Regalarminfo regalarminfo) {
		int rows=regalarminfoService.saveObject(dBname,regalarminfo);
		
		if (rows==1) {
			
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/deleteByIds")
	public SysResult deleteObject(@PathVariable String dBname,Integer...ids) {
		int rows=regalarminfoService.deleteObjectByIds(dBname,ids);
		if(rows==ids.length) {
			
			return SysResult.oK("删除了"+rows+"条信息");
		}
		return SysResult.build(50009, "删除失败，或者信息不存在");
	}
	@RequestMapping("/deleteByRegId")
	public SysResult deleteObjectByRegId(@PathVariable String dBname,Integer regId) {
		int rows=regalarminfoService.deleteObjectByRegId(dBname,regId);
		if(rows>0) {
			
			return SysResult.oK("删除了"+rows+"条信息");
		}
		return SysResult.build(50009, "删除失败，或者信息不存在");
	}
	@RequestMapping("/update")
	public SysResult updateObject(@PathVariable String dBname,Regalarminfo regalarminfo) {
		int rows=regalarminfoService.updateObject(dBname,regalarminfo);
		if (rows==1) {
			
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/export")
	public void exportExcel(@PathVariable String dBname,Integer regId,HttpServletResponse response) {
		int rowCount=regalarminfoService.getRowCount(dBname,regId);
		if(rowCount<20000) {
			List<Regalarminfo>list= new ArrayList<>();
			list=regalarminfoService.findAllByRegId(dBname,regId);
			ExcelUtil.exportExcel(list, "报警条件列表", "报警条件列表", Regalarminfo.class, dBname+"-报警条件.xls", response);
		}else {
			Map<String, String>queryParams=new HashMap<>();
			queryParams.put("dBname", dBname);
			ExcelUtil.exportbigExcel("报警条件列表", "报警条件列表", queryParams, regalarminfoService, Regalarminfo.class, dBname+"-报警条件.xls", response);
		}
	
	}
	@RequestMapping("/import")
	public SysResult importExcel(@PathVariable String dBname,MultipartFile file) {
		List<Regalarminfo>list =ExcelUtil.importExcel(file, 1, 1, Regalarminfo.class);
		try {
			regalarminfoService.importObjects(dBname, list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "保存失败");
		}
		return SysResult.oK();
	}
}
