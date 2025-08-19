package com.photonstudio.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.QsUserloginlog;
import com.photonstudio.service.QsUserloginlogService;

@RestController
@RequestMapping("/zsqy/qsUserloginlog")
public class QsUserloginlogController {
	@Autowired
	private QsUserloginlogService qsUserloginlogService;
	@RequestMapping("/{dBname}/findObject")
	public SysResult findObject(@PathVariable String dBname,String username,
				                   Integer pageCurrent,Integer pageSize,
				                      Date startTime,Date endTime) {
		PageObject<QsUserloginlog> pageObject=new PageObject<>();
		try {
			pageObject = qsUserloginlogService
					.findObject(dBname,username,pageCurrent,pageSize,startTime,endTime);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		return SysResult.oK(pageObject);
		
	}
	@RequestMapping("/{dBname}/save")
	public SysResult saveObject(@PathVariable String dBname,QsUserloginlog qsUserloginlog,HttpServletRequest request) {
		int rows=qsUserloginlogService.saveObject(dBname,qsUserloginlog,request);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/{dBname}/delete")
	public SysResult deleteObject(@PathVariable String dBname,Integer...ids) {
		int rows=qsUserloginlogService.deleteObjectById(dBname,ids);
		if(rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者部分记录已经不存在");
	}
	@RequestMapping("/{dBname}/update")
	private SysResult updateObject(@PathVariable String dBname,QsUserloginlog qsUserloginlog) {
		int rows=qsUserloginlogService.updateObject(dBname,qsUserloginlog);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "更新失败");
	}
}
