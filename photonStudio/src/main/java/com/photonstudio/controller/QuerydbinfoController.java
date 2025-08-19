package com.photonstudio.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Querydbinfo;
import com.photonstudio.service.QuerydbinfoService;
/**
 * 其他数据库查询的配置信息
 * @author 16526
 *
 */

@RestController
@RequestMapping("/zsqy/Otherdb/{dBname}")
public class QuerydbinfoController {
	@Autowired
	private QuerydbinfoService querydbinfoService;
	@RequestMapping("/findObjectByOtherdb")//其他数据库查询数据
	public SysResult findObjectByOtherdb(@PathVariable String dBname,Integer id,@RequestParam Map<String, String> param) {
		SysResult rs=new SysResult();
		try {
			//rs = querydbinfoService.findObjectByOtherdb(dBname,id,param);
			rs=querydbinfoService.findObjectByOtherdb23(dBname,id,param);
			return rs;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	@RequestMapping("/findObject")
	public SysResult findObject(@PathVariable String dBname,Integer pageCurrent,Integer pageSize) {
		PageObject<Querydbinfo>list=new PageObject<>();
		try {
			list=querydbinfoService.findObject(dBname,pageCurrent,pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		return SysResult.oK(list);
	}
	@RequestMapping("/findObjectById")
	public SysResult findObjectById(@PathVariable String dBname,Integer id) {
		Querydbinfo querydbinfo=new Querydbinfo();
		try {
			querydbinfo = querydbinfoService.findObjectById(dBname,id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (querydbinfo==null) {
			return SysResult.build(50009, "未找到查询模板");
		}
		return SysResult.oK(querydbinfo);
	}
	@RequestMapping("/save")
	public SysResult saveObject(@PathVariable String dBname, Querydbinfo querydbinfo) {
		int rows=querydbinfoService.saveObject(dBname,querydbinfo);
		if(rows==1) {
			return SysResult.oK();		
			}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/delete")
	public SysResult deleteObjectByid(@PathVariable String dBname,Integer id) {
		int rows=querydbinfoService.deleteObjectByid(dBname,id);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者数据已经不存在");
	}
	@RequestMapping("/update")
	public SysResult updateObject(@PathVariable String dBname, Querydbinfo querydbinfo) {
		int rows=querydbinfoService.updateObject(dBname,querydbinfo);
		if(rows==1) {
			return SysResult.oK();		
			}
		return SysResult.build(50009, "更新失败");
	}
		
}
