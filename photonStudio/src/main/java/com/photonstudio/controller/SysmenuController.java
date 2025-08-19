package com.photonstudio.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Sysmenu;
import com.photonstudio.service.SysmenuService;

@Controller
@RequestMapping("/zsqy/menu")
public class SysmenuController {
		  @Autowired
         private SysmenuService sysmenuService;
	@RequestMapping("/findAll")
	@ResponseBody
	public SysResult findAll() {
		List<Sysmenu>list=sysmenuService.findAll();
		return SysResult.oK(list);
	}	
	@RequestMapping("/findmenu")
	@ResponseBody
	public SysResult findmenuByParentId(Integer parentId) {
		System.out.println("查询父Id为"+parentId+"的菜单");
		if(parentId==null) {
			parentId=0;
		}
		List<Sysmenu> list=new ArrayList<>();
		list=sysmenuService.findmenuByParentId(parentId);
		return SysResult.oK(list);
	}
	@RequestMapping("/savemenu")
	@ResponseBody
	private SysResult savemenu(Sysmenu menu) {
		System.out.println(menu);
		int rows=sysmenuService.savemenu(menu);
		if(rows==1) {
		return	SysResult.oK();
		}
		return SysResult.build(50009, "失败");
	}
	@RequestMapping("/update")
	@ResponseBody
	public SysResult update(Sysmenu menu) {
		int rows=sysmenuService.updateMenu(menu);
		
		if(rows!=1) {
			return SysResult.build(50009, "失败");
		}
		return SysResult.oK();
	}
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deletemenuById(Integer id) {
		System.out.println(id);
		int rows=sysmenuService.deleteMenuById(id);
         if(rows==1) {
        	 return new SysResult("已经删除");
         }
         return SysResult.build(50009, "失败");
	}
	
	@RequestMapping("/findAllMenu")
	@ResponseBody
	public SysResult findAllMenu( ) {
		List<Sysmenu>menulist=sysmenuService.findAllMenu();
		return SysResult.oK(menulist);
	}		
}

