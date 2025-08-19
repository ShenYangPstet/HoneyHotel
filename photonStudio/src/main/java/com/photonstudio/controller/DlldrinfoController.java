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
import com.photonstudio.pojo.Dlldrinfo;
import com.photonstudio.pojo.Drllinfo;
import com.photonstudio.pojo.Drlltype;
import com.photonstudio.service.DlldrinfoService;

@RestController
@RequestMapping("/zsqy/dlldrinfo/{dBname}")
public class DlldrinfoController {
	@Autowired
	private DlldrinfoService dlldrinfoService;
	@RequestMapping("/findObject")
	public SysResult findObject(@PathVariable String dBname,String drname
								,Integer pageCurrent,Integer pageSize ) {
		PageObject<Dlldrinfo> pageObject=new PageObject<>();
		try {
			pageObject=dlldrinfoService.findObject(dBname,drname,pageCurrent,pageSize);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
									return SysResult.oK(pageObject);
	}
	@RequestMapping("/save")//新增记录
	public SysResult saveObject(@PathVariable("dBname")String dBname,Dlldrinfo dlldrinfo) {
		int rows=dlldrinfoService.saveObject(dBname,dlldrinfo);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败");
	}
	@RequestMapping("/delete")
	public SysResult delete(@PathVariable("dBname")String dBname,Integer...ids) {
		int rows=dlldrinfoService.deleteObjectById(dBname,ids);

		if (rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败，结果可能已经不存在");
	}
	@RequestMapping("/update")
	public SysResult update(@PathVariable("dBname")String dBname,Dlldrinfo dlldrinfo) {
		System.out.println("dlldrinfo===="+dlldrinfo);
		int rows=dlldrinfoService.updateObject(dBname,dlldrinfo);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "修改失败");
	}
	@RequestMapping("/export")
	public void exportQstag(@PathVariable String dBname,String drname,HttpServletResponse response) {
		List<Dlldrinfo>list=new ArrayList<>();
		try {
			list=dlldrinfoService.findDlldrinfo(dBname,drname);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		ExcelUtil.exportExcel(list, "通讯设备地址", "通讯设备地址", Dlldrinfo.class, dBname+"-通讯设备地址.xls", response);
	}
	@RequestMapping("/import")
	public SysResult importExcel(@PathVariable String dBname,MultipartFile file) {
		List<Dlldrinfo>list =ExcelUtil.importExcel(file, 1, 1, Dlldrinfo.class);
		try {
			dlldrinfoService.importObjects(dBname, list);
		} catch (Exception e) {
			
			e.printStackTrace();
			return SysResult.build(50009, "保存失败");
		}
		return SysResult.oK();
	}
	@RequestMapping("/findDrllinfo")
	public SysResult findDrllinfo(@PathVariable String dBname) {
		List<Drllinfo>list=new ArrayList<>();
				try {
					list=dlldrinfoService.findDrllinfo(dBname);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return SysResult.build(50009, "查询失败");
				}
		return SysResult.oK(list);
	}
	@RequestMapping("/findDlldrinfo")
	public SysResult findDlldrinfo(@PathVariable String dBname) {
		List<Dlldrinfo>list=new ArrayList<>();
				try {
					list=dlldrinfoService.findDlldrinfo(dBname, null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return SysResult.build(50009, "查询失败");
				}
		return SysResult.oK(list);
	}
	@RequestMapping("/findDrlltype")
	public SysResult findDrlltypeBydrid(@PathVariable String dBname,Integer drid) {
		List<Drlltype>list=new ArrayList<>();
		try {
			list=dlldrinfoService.findDrlltypeBydrid(dBname,drid);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		return SysResult.oK(list);
	}
}
