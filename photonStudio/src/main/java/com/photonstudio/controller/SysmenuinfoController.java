package com.photonstudio.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.IPUtil;
import com.photonstudio.common.ObjectMapperUtil;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Sysmenuinfo;
import com.photonstudio.service.SysmenuinfoService;

@RestController
@RequestMapping("/zsqy/sysmenuinfo")
public class SysmenuinfoController {
	@Autowired
	private SysmenuinfoService sysmenuinfoService;
	@RequestMapping("/{dBname}/save")
	public SysResult saveAll(@PathVariable String dBname,String sysmenuinfo) {
		List<Sysmenuinfo> list=null;
		if(sysmenuinfo!=null||sysmenuinfo!="") {
		
		list=ObjectMapperUtil.toListObject(sysmenuinfo, Sysmenuinfo.class);
		}
		int rows=sysmenuinfoService.saveAll(dBname,list);
		if(rows==list.size()) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "部分信息未插入");
	}
	@RequestMapping("/{dBname}/update")
	public SysResult updateObject(@PathVariable String dBname,Sysmenuinfo sysmenuinfo) {
		int rows=sysmenuinfoService.updateObject(dBname,sysmenuinfo);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "更新失败");
	}
	@RequestMapping("/{dBname}/findObject")
	public SysResult findObjectByParentId(@PathVariable String dBname,Integer parentId) {
		
		List<Sysmenuinfo> list=sysmenuinfoService.findObjectByParentId(dBname,parentId);
		return SysResult.oK(list);
	}
	@RequestMapping("/{dBname}/findAllMenu")
	public SysResult findAllMenu(@PathVariable String dBname) {
		List<Sysmenuinfo>menulist=sysmenuinfoService.findAllMenu(dBname);
		return SysResult.oK(menulist);
	}
	@RequestMapping("/{dBname}/findObjectByGroupid")
	public SysResult findObjectByGroupid(@PathVariable String dBname,Integer groupid,
										HttpServletRequest request) {
		List<Sysmenuinfo>list=sysmenuinfoService.findObjectByGroupid(dBname,groupid);
		String ip=IPUtil.getIp(request);
		return SysResult.oK(list);
	}
	@RequestMapping("/{dBname}/findMenuIdByGroupid")
	public SysResult findMenuIdByGroupid(@PathVariable String dBname,Integer groupid) {
		System.out.println("groupid==="+groupid);
		
		List<Integer> list=new ArrayList<>();
		try {
			list = sysmenuinfoService.findMenuIdByGroupid(dBname,groupid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		
		return SysResult.oK(list);
	}
	
	@RequestMapping("/{dBname}/findPicByMenuId")
	public SysResult findPicByMenuId(@PathVariable String dBname,Integer id) {
		Sysmenuinfo sysmenuinfo=sysmenuinfoService.findPicByMenuId(dBname,id);
		
		return SysResult.oK(sysmenuinfo);
	}
}
