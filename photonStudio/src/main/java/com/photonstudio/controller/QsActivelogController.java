package com.photonstudio.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.QsActivelog;
import com.photonstudio.service.QsActivelogService;

@RestController
@RequestMapping("/zsqy/qsActivelog")
public class QsActivelogController {
	@Autowired
	private QsActivelogService qsActivelogService;
	@RequestMapping("/{dBname}/findObject")
	public SysResult findObject(@PathVariable String dBname,String username,
            Integer pageCurrent,Integer pageSize,
            Date startTime,Date endTime) {
		PageObject<QsActivelog>pageObject=new PageObject<>();
		try {
			pageObject=qsActivelogService.findObject(dBname,username,
					pageCurrent,pageSize,startTime,endTime);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		return SysResult.oK(pageObject);
	}
	@RequestMapping("/{dBname}/save")
	public SysResult saveObject(@PathVariable String dBname,QsActivelog qsActivelog) {
		int rows=qsActivelogService.saveObject(dBname,qsActivelog);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/{dBname}/delete")
	public SysResult deleteObject(@PathVariable String dBname,Integer...ids) {
		int rows=qsActivelogService.deleteObject(dBname,ids);
		if(rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者有记录已经不存在");
	}
	@RequestMapping("/{dBname}/update")
	public SysResult update(@PathVariable String dBname,QsActivelog qsActivelog) {
		int rows=qsActivelogService.updateObject(dBname,qsActivelog);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "更新失败");
	}
}
