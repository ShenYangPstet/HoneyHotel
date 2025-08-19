package com.photonstudio.controller;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.StrUtil;
import com.photonstudio.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Appinfo;
import com.photonstudio.pojo.Appusergroup;
import com.photonstudio.pojo.Sysmenuinfo;

@RestController
@Api(tags = "项目信息权限接口")
@RequestMapping("/zsqy/appusergroup")
public class AppusergroupController {
	@Autowired
	private AppusergroupService appusergroupService;
	@Autowired
	private DrinfoService drinfoService;
	@Autowired
	private SysmenuinfoService sysmenuinfoService;
	@Autowired
	private AppsysuserService appsysuserService;
	@Autowired
	private AppinfoService appinfoService;
	@RequestMapping("/findObject")
	public SysResult findObject(Integer userid,Integer pageCurrent,Integer pageSize) {
		PageObject<Appusergroup>pageObject=appusergroupService.
				findObject(userid,pageCurrent ,pageSize );
		return SysResult.oK(pageObject);	
		}
	@RequestMapping("/save")
	private SysResult saveObject(Appusergroup appusergroup) {
		int rows=appusergroupService.saveObject(appusergroup);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
		
	}
	@RequestMapping("/findObjectByProvinces")
	public SysResult findObjectByDZ(Integer userid,String region,String city) {
		List<Appusergroup> list = new ArrayList<>();
		list = appusergroupService.findObjectByuserid(userid,region,city);
		for (Appusergroup appusergroup : list) {
			String dBname = appusergroup.getAppid() + appusergroup.getAppName();
			int drCount = drinfoService.getRowCount(dBname, null, null);
			List<Sysmenuinfo> menuList = sysmenuinfoService.findObjectByGroupid(dBname,
					appusergroup.getAppuserground());
			appusergroup.setDrCount(drCount);
			appusergroup.setMenuList(menuList);
			Appinfo appinfo = appsysuserService.queryAppinfoByAppname(dBname, appusergroup.getAppName());
			appusergroup.setAppinfo(appinfo);
		}
		// System.out.println("appusergroup==="+list);
		return SysResult.oK(list);
	}

	@RequestMapping("/findObjectByUser")//app页面首页项目信息
	public SysResult findObjectByUser(Integer userid,Integer appid,String appexplain) {
		List<Appusergroup> list = new ArrayList<>();
		list = appusergroupService.findObjectByuserid(userid,null,null);
		for (Appusergroup appusergroup : list) {
			String dBname = appusergroup.getAppid() + appusergroup.getAppName();
			Appinfo appinfo = appsysuserService.queryAppinfoByAppname(dBname, appusergroup.getAppName());
			int drCount = drinfoService.getRowCount(dBname, null, null);
			appusergroup.setAppinfo(appinfo);
			appusergroup.setDrCount(drCount);
			appusergroup.setAppSumday(appinfoService.findAppinfo(dBname));
		}
		// System.out.println("appusergroup==="+list);
		return SysResult.oK(list);
	}

	@RequestMapping("/delete")
	public SysResult deleteById(Appusergroup appusergroup) {
		int rows=appusergroupService.deleteObjectById(appusergroup);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败，或者有信息已经不存在");
	}
	@RequestMapping("/update")
	public SysResult updeateObject(Appusergroup appusergroup) {
		int rows=appusergroupService.updateObject(appusergroup);
		if(rows==1) {
			return SysResult.oK();		
		}
		return SysResult.build(50009, "更新失败");
		
	}


	@PostMapping("/updateSkin")
	@ApiOperation("根据id修改皮肤")
	@ApiImplicitParams({@ApiImplicitParam(name = "id",value = "主键id",dataType = "int",required = true),
			@ApiImplicitParam(name = "skin",value = "皮肤" ,dataType = "String",required = true)
	})
	public SysResult updateSkinById( Integer id, String skin){
		if (StrUtil.isEmpty(skin))return SysResult.build(50009,"皮肤参数不能为空");
		int rows=appusergroupService.updateSkinById(id,skin);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "更新失败");
	}
}
