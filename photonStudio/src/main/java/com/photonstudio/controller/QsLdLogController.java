package com.photonstudio.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.ExcelUtil;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.QsLdLog;
import com.photonstudio.service.QsLdLogService;

@RestController
@RequestMapping("/zsqy/qsLdLog/{dBname}")
public class QsLdLogController {
	@Autowired
	private QsLdLogService qsLdLogService;
	@RequestMapping("/findObject")
	public SysResult findObject(@PathVariable String dBname,Integer type,Integer state,
								Integer	pageCurrent,Integer pageSize,
								Date startTime,Date endTime) {
		PageObject<QsLdLog>pageObject=new PageObject<>();
		try {
			pageObject = qsLdLogService.
					findObject(dBname,pageCurrent,pageSize,type,
								state,startTime,endTime);
			return SysResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "查询失败");
	}
	@RequestMapping("/save")
	public SysResult saveObject(@PathVariable String dBname,QsLdLog qsLdLog) {
		int rows=qsLdLogService.saveObject(dBname,qsLdLog);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/delete")
	public SysResult deleteObjectById(@PathVariable String dBname,Integer...ids) {
		int rows =qsLdLogService.deleteObjectById(dBname,ids);
		if (rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者记录已经不存在");
	}
	@RequestMapping("/update")
	public SysResult updateObject(@PathVariable String dBname,QsLdLog qsLdLog) {
		int rows=qsLdLogService.updateObject(dBname,qsLdLog);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "更新失败");
	}
	@RequestMapping("/export")
	public void exportExcel(@PathVariable String dBname,Date startTime,Date endTime,
								HttpServletResponse response) {
		List<QsLdLog>list=qsLdLogService.findAll(dBname,startTime,endTime);
		ExcelUtil.exportExcel(list, "联动日志", "联动日志", QsLdLog.class, dBname+"-联动日志.xls", response);
	}
}
