package com.photonstudio.controller;

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
import com.photonstudio.pojo.Assetstype;
import com.photonstudio.service.AssetstypeServcie;

@RestController
@RequestMapping("/zsqy/Assetstype")
public class AssetstypeController {
	@Autowired
	private AssetstypeServcie assetstypeServcie;
	@RequestMapping("/{dBname}/findObject")
    public SysResult findObject(@PathVariable("dBname") String dBname,
    		          String assetstypename,Integer pageCurrent,Integer pageSize) {
		PageObject<Assetstype> pageObject=new PageObject<>();
    try {
		pageObject=assetstypeServcie.findObjectByName(
			dBname,assetstypename,pageCurrent, pageSize);
			return SysResult.oK(pageObject);
	} catch (Exception e) {
		e.printStackTrace();
	return SysResult.build(50009, "查询失败");
	}
	}
	@RequestMapping("/{dBname}/findAll")
	public SysResult findAll(@PathVariable String dBname) {
		List<Assetstype> list = assetstypeServcie.findAll(dBname);
		return SysResult.oK(list);
	}
	@RequestMapping("/{dBname}/save")
	public SysResult saveObject(@PathVariable("dBname")String dBname,
								Assetstype assetstype) {
		int rows=assetstypeServcie.saveObject(dBname,assetstype);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败");
	}
	@RequestMapping("/{dBname}/delete")
	public SysResult deleteObjectById(@PathVariable("dBname")String dBname,
									Integer...ids ) {
		int rows=assetstypeServcie.deleteObject(dBname,ids);
		if (rows==0) {
			return SysResult.build(50009, "记录可能已经不存在");
		}
		return SysResult.oK();
	}
	@RequestMapping("/{dBname}/update")
	public SysResult updateObject(@PathVariable("dBname")String dBname,
			                 Assetstype assetstype) {
		int rows=assetstypeServcie.updateObject(dBname,assetstype);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "更新失败");
	}
	@RequestMapping("/{dBname}/export")
	public void exportExcel(@PathVariable String dBname,HttpServletResponse response) {
		List<Assetstype>list=assetstypeServcie.findAll(dBname);
		ExcelUtil.exportExcel(list, "资产类型", "资产类型", Assetstype.class, dBname+"资产类型.xls", response);
	}
	@RequestMapping("/{dBname}/import")
	public SysResult importExcel(@PathVariable String dBname,MultipartFile file) {
		List<Assetstype>list=ExcelUtil.importExcel(file, 1, 1, Assetstype.class);
		try {
			assetstypeServcie.importObject(dBname,list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("数据导入失败");
		}
		return SysResult.oK();
	}
}
