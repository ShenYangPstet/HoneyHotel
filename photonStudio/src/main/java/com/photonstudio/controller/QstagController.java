package com.photonstudio.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.photonstudio.common.enums.Status;
import com.photonstudio.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.photonstudio.common.ExcelUtil;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Qstag;
import com.photonstudio.service.QstagService;

@RestController
@RequestMapping("/zsqy/qstag/{dBname}")
public class QstagController {
	@Autowired
	private QstagService qstagService;
	@RequestMapping("/findObject")
	public SysResult findObject(@PathVariable String dBname,String tagname,Integer itemdrid
								,Integer pageCurrent,Integer pageSize ) {
		PageObject<Qstag> pageObject=new PageObject<>();
		try {
			pageObject=qstagService.findObject(dBname,tagname,itemdrid,pageCurrent,pageSize);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
									return SysResult.oK(pageObject);
	}

	@GetMapping("/findTagValueByTagName")
	public SysResult findTagValueByTagName(String...tagname){
		List<Qstag>list;
		try {
			list=qstagService.findTagValueByTagName(tagname);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009,"查询失败");
		}
		return SysResult.oK(list);
	}

	@RequestMapping("/save")//新增记录
	public SysResult saveObject(@PathVariable("dBname")String dBname,Qstag qstag) {
		int rows=qstagService.saveObject(dBname,qstag);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败");
	}
	@RequestMapping("/delete")
	public SysResult delete(@PathVariable("dBname")String dBname,Integer...ids) {
		int rows=qstagService.deleteObjectById(dBname,ids);

		if (rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败，结果可能已经不存在");
	}
	@RequestMapping("/update")
	public SysResult update(@PathVariable("dBname")String dBname,Qstag qstag) {
		int rows=qstagService.updateObject(dBname,qstag);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "修改失败");
	}
	@RequestMapping("/updateByTagname")
	public SysResult updateByTagName(@PathVariable("dBname")String dBname, String msg) {
		int rows=qstagService.updateByTagName(dBname,msg);
		if (rows>0) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "修改失败或部分未修改");
	}
	@RequestMapping("/export")
	public void exportQstag(@PathVariable String dBname,String tagname,Integer itemdrid,HttpServletResponse response) {
		int row=qstagService.findExportCount(dBname,tagname,itemdrid);
		if(row<20000) {
			List<Qstag>list=new ArrayList<>();
			list=qstagService.findQstag(dBname,tagname,itemdrid);
			ExcelUtil.exportExcel(list, "变量寄存器", "寄存器", Qstag.class, dBname+"-寄存器.xls", response);
		}else {
			Map<String, String>queryParams=new HashMap<>();
			queryParams.put("dBname", dBname);
			String stritemdrid=null;
			if(itemdrid!=null)stritemdrid=String.valueOf(itemdrid);
			queryParams.put("itemdrid",stritemdrid);
			queryParams.put("tagname",tagname);
			ExcelUtil.exportbigExcel("变量寄存器", "寄存器", queryParams, qstagService, Qstag.class, dBname+"-寄存器.xls", response);
		
		}
		
	}
	@RequestMapping("/import")
	public SysResult importExcel(@PathVariable String dBname,MultipartFile file) {
		List<Qstag>list =ExcelUtil.importExcel(file, 1, 1, Qstag.class);
		try {
			qstagService.importObjects(dBname, list);
		} catch (Exception e) {
			
			e.printStackTrace();
			return SysResult.build(50009, "保存失败");
		}
		return SysResult.oK();
	}

	@GetMapping("/fidnudge")
	public Result fidnudge(String tagname){
        int result = qstagService.fidnudge(tagname);
		if (result>0){
		 return Result.ok(true);
		}else {
			return Result.build(Status.RECORD_NONEXIST.code,"寄存器不存在");
		}

	}
}
