package com.photonstudio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Appsysuser;
import com.photonstudio.pojo.Appusergroup;
import com.photonstudio.service.AppsysuserService;
import com.photonstudio.service.AppusergroupService;

@Controller
@RequestMapping("/zsqy/user")
public class AppsysuserController {
	@Autowired
 private AppsysuserService appsysuserService;
	@Autowired
	private AppusergroupService appusergroupService;
	
	@RequestMapping("/findUser")
	@ResponseBody
	public SysResult findUserByName(String username,Integer pageCurrent,Integer pageSize) {
		
		PageObject<Appsysuser> pageObject= appsysuserService.findUserByName(username,pageCurrent,pageSize);
		return SysResult.oK(pageObject);
	}
	@RequestMapping("/findAll")
	@ResponseBody
	public SysResult findAll() {
		List<Appsysuser>list=appsysuserService.findAll();
		return SysResult.oK(list);
	}
	@RequestMapping("/findUserByName")
	@ResponseBody
	public SysResult findAppsysuserByUsername(String username) {
		if(StringUtils.isEmpty(username))return SysResult.build(50009, "用户名不能为空");
		Appsysuser user=appsysuserService.findAppsysuserByUsername(username);
		if(!StringUtils.isEmpty(user)) {
			return SysResult.build(50009, "用户已经存在");
		}
		return SysResult.oK();
	}
	
	@RequestMapping("/check")
	@ResponseBody
	public SysResult findCheckUser(String username) {
		boolean flag = appsysuserService.findCheckUser(username);
		return SysResult.oK(flag);
	}
	
	@ResponseBody
	@PostMapping("/saveUser" )
	public SysResult saveUser(Appsysuser user) {
		int rows;
		
	rows=appsysuserService.saveObject(user);
	if(rows==1) {
		return SysResult.oK();
	}
	 return SysResult.build(50009, "失败");
	}
	@RequestMapping("/deleteUser")
	@ResponseBody
	public SysResult deleteObjectById(Integer...ids) {
		List<Appusergroup>list=appusergroupService.findObjectByUserIds(ids);
		if(list!=null&&list.size()>0) {
			for (Appusergroup appusergroup : list) {
				appusergroupService.deleteObjectById(appusergroup);
			}
		}
		int rows=appsysuserService.deleteObjectById(ids);
		return SysResult.build(20000, "删除了"+rows+"条信息");
	}
	@RequestMapping("/updateUser")
	@ResponseBody
	public SysResult updateObject(Appsysuser user) {
		int rows=appsysuserService.updateObject(user);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败");
	}
	@RequestMapping("/updatePassword")
	@ResponseBody
	public SysResult updatePassword(String username,String password,String newpassword) {
		int rows=appsysuserService.findUserByPassword(username,password);
		if(rows==0) {
			return SysResult.build(50009, "密码错误");
		}
		
		int rows1=appsysuserService.updatePassword(username,newpassword);
		if(rows1==0) {
			return SysResult.build(50009, "更新密码失败");
		}
		return SysResult.oK();
	}
}
