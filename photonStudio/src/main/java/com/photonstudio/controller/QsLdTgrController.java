package com.photonstudio.controller;

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
import com.photonstudio.pojo.DateCalendar;
import com.photonstudio.pojo.QsLdTgr;
import com.photonstudio.pojo.Timemodeinfo;
import com.photonstudio.service.QsLdTgrService;
import com.photonstudio.service.TimeModeService;
import com.photonstudio.service.TimemodejobService;

@RestController
@RequestMapping("/zsqy/qsLdTgr/{dBname}")
public class QsLdTgrController {
	@Autowired
	private QsLdTgrService qsLdTgrService;
	@Autowired
	private TimeModeService timeModeService;
	@Autowired
	private TimemodejobService timemodejobService;
	@RequestMapping("findObject")
	public SysResult findObject(@PathVariable String dBname,Integer tjType,
			Integer pageCurrent,Integer pageSize) {
		PageObject<QsLdTgr>pageObject=new PageObject<>();
		try {
			pageObject=qsLdTgrService.findObject(dBname,tjType,pageCurrent,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return SysResult.oK(pageObject);
	}
	@RequestMapping("/save")
	public SysResult saveObject(@PathVariable String dBname,QsLdTgr qsLdTgr) {
		int rows=qsLdTgrService.saveObject(dBname,qsLdTgr);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/delete")
	public SysResult deleteObject(@PathVariable String dBname,Integer tjType,Integer...ids) {
		if(tjType==2) {
			List<Timemodeinfo>timemodeinfolist= timeModeService.findModeinfoByTjIds(dBname, ids);
			if(timemodeinfolist!=null&&timemodeinfolist.size()>0) {
				
				Integer[] tjids=new Integer[timemodeinfolist.size()];
				for (int i = 0; i < tjids.length; i++) {
					tjids[i]=timemodeinfolist.get(i).getId();
				}
				timeModeService.deleteObjectinfo(dBname, tjids);
				timemodejobService.deleteObjectByTjIds(dBname, tjids);
			}
		}
		int rows=qsLdTgrService.deleteObjectById(dBname,tjType,ids);
		if(rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009,"删除失败或者有信息已经不存在");
	}
	@RequestMapping("/update")
	public SysResult updateObject(@PathVariable String dBname,QsLdTgr qsLdTgr,Integer appid) {
		int rows=qsLdTgrService.updateObject(dBname,qsLdTgr,appid);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/export")
	public void  exportExcel(@PathVariable String dBname,Integer tjType,HttpServletResponse response) {
		List<QsLdTgr>list=qsLdTgrService.findAllByRwType(dBname,tjType);
		ExcelUtil.exportExcel(list, "联动任务条件", "任务条件", QsLdTgr.class, dBname+"-联动任务条件.xls", response);
	}
	@RequestMapping("/import")
	public SysResult importExcel(@PathVariable String dBname,Integer tjType,MultipartFile file) {
		List<QsLdTgr>list=ExcelUtil.importExcel(file, 1, 1, QsLdTgr.class);
		
			try {
				qsLdTgrService.importObjects(dBname,tjType, list);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("保存失败");
			}
		return SysResult.oK();
	}
	
	@RequestMapping("/findDate")
	public SysResult findDate(String year,String month) {
		try {
			List<DateCalendar> dateList=qsLdTgrService.findDate(year,month);
			return SysResult.oK(dateList);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	@RequestMapping("/savebydate")
	public SysResult savebydate(@PathVariable String dBname,String tgName,Integer rwid,String dateArry) {
		try
		{
			int rows=qsLdTgrService.savebydate(dBname,tgName,rwid,dateArry);
			if(rows>0) {
				return SysResult.oK();
			}
			else
			{
				return SysResult.build(50009, "保存失败");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "保存失败");
		}
	}
}
