package com.photonstudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Queryconditioninfo;
import com.photonstudio.service.QueryconditioninfoService;

@RestController
@RequestMapping("/zsqy/queryconditioninfo/{dBname}")
public class QueryconditioninfoController {
	@Autowired
	private QueryconditioninfoService queryconditioninfoService;
	@RequestMapping("/findObject")
	public SysResult findObject(@PathVariable String dBname,Integer conditionId,
						Integer pageCurrent,Integer pageSize) {
		PageObject<Queryconditioninfo>list=new PageObject<>();
		try {
			list=queryconditioninfoService.findObject(dBname,conditionId,pageCurrent,pageSize);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		return SysResult.oK(list);
	}
	@RequestMapping("/save")
	private SysResult save(@PathVariable String dBname,Queryconditioninfo queryconditioninfo) {
		int rows=queryconditioninfoService.saveObject(dBname,queryconditioninfo);
		if(rows==1) {
			return SysResult.oK();		
			}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/delete")
	public SysResult deleteObjectByid(@PathVariable String dBname,Integer id) {
		int rows=queryconditioninfoService.deleteObjectByid(dBname,id);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者数据已经不存在");
	}
	@RequestMapping("update")
	public SysResult updateObject(@PathVariable String dBname, Queryconditioninfo queryconditioninfo) {
		int rows=queryconditioninfoService.updateObject(dBname,queryconditioninfo);
		if(rows==1) {
			return SysResult.oK();		
			}
		return SysResult.build(50009, "更新失败");
	}
}
