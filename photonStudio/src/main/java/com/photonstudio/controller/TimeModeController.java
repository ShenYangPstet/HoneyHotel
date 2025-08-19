package com.photonstudio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.QsLdTgr;
import com.photonstudio.pojo.Timemode;
import com.photonstudio.pojo.Timemodeinfo;
import com.photonstudio.pojo.YmdJob;
import com.photonstudio.service.TimeModeService;
import com.photonstudio.service.TimemodejobService;

@RestController
@RequestMapping("/zsqy/timemode")
public class TimeModeController {
	
	@Autowired
	private TimeModeService timeModeService;
	@Autowired
	private TimemodejobService timemodejobService;
	
	@RequestMapping("/{dBname}/findObject")//分页查询
	public SysResult findObject(@PathVariable("dBname") String dBname,Integer pageCurrent,Integer pageSize) {
		PageObject<Timemode> pageObject=new PageObject<>();
		try {
			pageObject=timeModeService.findObject(dBname,pageCurrent,pageSize);
			return SysResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
	}
	
	@RequestMapping("/{dBname}/findObjectinfo")//分页查询
	public SysResult findObjectinfo(@PathVariable("dBname") String dBname,Integer pageCurrent,Integer pageSize) {
		PageObject<Timemodeinfo> pageObject=new PageObject<>();
		try {
			pageObject=timeModeService.findObjectinfo(dBname,pageCurrent,pageSize);
			return SysResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
	}
	
	@RequestMapping("/{dBname}/findModeinfoByid")
	public SysResult findModeinfoByid(@PathVariable("dBname") String dBname,Integer id) {
		try {
			List<Timemodeinfo> list=timeModeService.findModeinfoByid(dBname,id);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
	}
	
	@RequestMapping("/{dBname}/findAllMode")
	public SysResult findAllMode(@PathVariable("dBname") String dBname) {
		try {
			List<Timemode> list=timeModeService.findAllMode(dBname);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
	}
	
	@RequestMapping("/{dBname}/findAllTg")
	public SysResult findAllTg(@PathVariable("dBname") String dBname) {
		try {
			List<QsLdTgr> list=timeModeService.findAllTg(dBname);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
	}
	
	@RequestMapping("/{dBname}/save")//新增记录
	public SysResult saveObject(@PathVariable("dBname")String dBname,Timemode timemode) {
		int rows=timeModeService.saveObject(dBname,timemode);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败");
	}
	
	@RequestMapping("/{dBname}/saveinfo")//新增记录
	public SysResult saveObjectinfo(@PathVariable("dBname")String dBname,Timemodeinfo timemodeinfo) {
		int rows=timeModeService.saveObjectinfo(dBname,timemodeinfo);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败");
	}
	
	@RequestMapping("/{dBname}/delete")
	public SysResult deleteObject(@PathVariable("dBname")String dBname,Integer...ids) {
		if(ids==null||ids.length==0)return SysResult.build(50009, "请选择！");
		timemodejobService.deleteObjectByTimemodeids(dBname, ids);
		int rows=timeModeService.deleteObject(dBname,ids);
		if (rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败，结果可能已经不存在");
	}
	
	@RequestMapping("/{dBname}/deleteinfo")
	public SysResult deleteObjectinfo(@PathVariable("dBname")String dBname,Integer...ids) {
		if(ids==null||ids.length==0)return SysResult.build(50009, "请选择！");
		List<Timemodeinfo>infolist=timeModeService.findObjectinfoByIds(dBname,ids);
		if(infolist!=null&&infolist.size()>0) {
			Integer[] tjIds=new Integer[infolist.size()];
			for (int i = 0; i < tjIds.length; i++) {
				tjIds[i]=infolist.get(i).getLdTjId();
			}
			timemodejobService.deleteObjectByTjIds(dBname, tjIds);
		}
		int rows=timeModeService.deleteObjectinfo(dBname,ids);
		if (rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败，结果可能已经不存在");
	}
	
	@RequestMapping("/{dBname}/update")
	public SysResult update(@PathVariable("dBname")String dBname,Timemode timemode) {
		int rows=timeModeService.updateObject(dBname,timemode);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "修改失败");
	}
	
	@RequestMapping("/{dBname}/updateinfo")
	public SysResult updateinfo(@PathVariable("dBname")String dBname,Timemodeinfo timemodeinfo) {
		int rows=timeModeService.updateObjectinfo(dBname,timemodeinfo);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "修改失败");
	}
	
	@RequestMapping("/{dBname}/findTimemodejob")//分页查询
	public SysResult findTimemodejob(@PathVariable("dBname") String dBname,String yearMonth) {
		try {
			List<YmdJob> ymdJobList=timeModeService.findTimemodejob(dBname,yearMonth);
			return SysResult.oK(ymdJobList);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
	}
	
	@RequestMapping("/{dBname}/saveTimemodejob")//分页查询
	public SysResult saveTimemodejob(@PathVariable("dBname") String dBname,String timemodes,Integer appid) {
		if(timemodes == null || timemodes.equals(""))
		{
			return SysResult.oK();
		}
		try {
			timeModeService.saveTimemodejob(dBname,timemodes,appid);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "保存失败");
		}
	}

}
