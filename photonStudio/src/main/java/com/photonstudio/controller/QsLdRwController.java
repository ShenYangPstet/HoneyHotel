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
import com.photonstudio.pojo.QsLdRw;
import com.photonstudio.service.QsLdJgrService;
import com.photonstudio.service.QsLdRwService;
import com.photonstudio.service.QsLdTgrService;
import com.photonstudio.service.TimemodejobService;

@RestController
@RequestMapping("/zsqy/QsLdRw/{dBname}")
public class QsLdRwController {
	@Autowired
	private QsLdRwService qsLdRwService;
	@Autowired
	private QsLdTgrService qsLdTgrService;
	@Autowired
	private QsLdJgrService qsLdJgrService;
	@Autowired
	private TimemodejobService timemodejobService;
	@RequestMapping("/findObject")
	public SysResult findObject(@PathVariable String dBname,
								Integer pageCurrent,Integer pageSize) {
		PageObject<QsLdRw>pageObject=new PageObject<>();
		try {
			pageObject=qsLdRwService.findObject(dBname,pageCurrent,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return SysResult.oK(pageObject);
	}
	
	@RequestMapping("/findMoshiObject")
	public SysResult findMoshiObject(@PathVariable String dBname,Integer rwType) {
		try {
			List<QsLdRw> qsLdRwList=qsLdRwService.findMoshiObject(dBname,rwType);
			return SysResult.oK(qsLdRwList);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	@RequestMapping("/findldRwByrwid")
	public SysResult findldRwByrwid(@PathVariable String dBname,Integer ldRwId) {
		try {
			QsLdRw qsLdRw=qsLdRwService.findObjectById(dBname,ldRwId);
			return SysResult.oK(qsLdRw);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	@RequestMapping("/findAll")
	public SysResult findAll(@PathVariable String dBname,Integer rwType) {
		List<QsLdRw>list=qsLdRwService.findAllByRwType(dBname, rwType);
		return SysResult.oK(list);
	}
	
	@RequestMapping("/save")
	private SysResult saveObject(@PathVariable String dBname,QsLdRw qsLdRw) {
		int rows=qsLdRwService.saveObject(dBname,qsLdRw);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/delete")
	private SysResult deleteObject(@PathVariable String dBname,Integer...ids) {
		for (Integer integer : ids) {
			
			System.out.println("任务参数=="+integer);
		}
		if(ids==null||ids.length==0)return SysResult.build(50009, "清选择");
			qsLdTgrService.deleteObjectByRwId(dBname,ids);
			qsLdJgrService.deleteObjectByRwId(dBname,ids);
			timemodejobService.deleteObjectByRwId(dBname, ids);
		int rows=qsLdRwService.deleteObjectById(dBname,ids);
		if(rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者有信息已经不存在");
	}
	@RequestMapping("/update")
	private SysResult updateObject(@PathVariable String dBname,QsLdRw qsLdRw)  {
		int rows=qsLdRwService.updateObject(dBname,qsLdRw);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "更新失败");
	}
	@RequestMapping("/export")
	public void exportExcel(@PathVariable String dBname,Integer rwType,HttpServletResponse response) {
		List<QsLdRw>list=qsLdRwService.findAllByRwType(dBname,rwType);
		ExcelUtil.exportExcel(list, "联动任务", "联动任务", QsLdRw.class, dBname+"-联动任务.xls", response);
	}
	@RequestMapping("/import")
	public SysResult importExcel(@PathVariable String dBname,MultipartFile file) {
		List<QsLdRw>list=ExcelUtil.importExcel(file, 1, 1,QsLdRw.class);
		try {
			qsLdRwService.importObjects(dBname,list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return SysResult.oK();
	}
	@RequestMapping("/startJobByLdRwId")
	public SysResult startJobByLdRwId(@PathVariable String dBname,Integer ldRwId,Integer appid) {
		QsLdRw qsLdRw=qsLdRwService.findObjectById(dBname,ldRwId);
		if(qsLdRw==null) return SysResult.build(50009, "任务不存在");
		
			boolean falg=qsLdRwService.startJobByLdRwId(dBname,ldRwId,appid,qsLdRw);
		if(!falg) {
			return SysResult.build(50009, "创建任务失败");
		}
		return SysResult.oK();
	}
	@RequestMapping("/deleteJobByLdRwId")
	public SysResult deleteRegJob(@PathVariable String dBname,Integer appid,Integer ldRwId) {
		
		boolean falg=qsLdRwService.deleteRegJob(dBname, ldRwId);
		if(!falg)
			return SysResult.build(50009," 删除任务失败");
		QsLdRw qsLdRw=new QsLdRw();
			qsLdRw.setLdRwId(ldRwId);
			qsLdRw.setIssuspend(1);
			qsLdRwService.updateObject(dBname, qsLdRw);
		return SysResult.oK();
	}
	@RequestMapping("/startRegJobsByIds")
	public SysResult startRegJobsByids(@PathVariable String dBname,Integer appid,Integer... ids) {
		if(ids==null||ids.length==0)return SysResult.build(50009, "请选择任务！");
		List<QsLdRw>rwlist=qsLdRwService.findObjectByIds(dBname, ids);
		int count=0;
		for (QsLdRw qsLdRw : rwlist) {
			boolean falg=qsLdRwService.startJobByLdRwId(dBname,qsLdRw.getLdRwId(),appid,qsLdRw);
		if(!falg) count++;
		}
		if(count>0) {
			return SysResult.build(50009, "有"+(count)+"个任务未启动");
		}
		return SysResult.oK();
				}
	
	@RequestMapping("/deleteRegJobsByIds")
	public SysResult deleteAllRegJobs(@PathVariable String dBname ,Integer... ids) {
		int count=0;
		if(ids==null||ids.length==0) return SysResult.build(50009, "请选择！");
			for (Integer id : ids) {
				boolean falg=qsLdRwService.deleteRegJob(dBname, id);
				QsLdRw qsLdRw=new QsLdRw();
				qsLdRw.setLdRwId(id);
				qsLdRw.setIssuspend(1);
				if(!falg) count++;
				qsLdRwService.updateObject(dBname, qsLdRw);
			
		}
		if(count>0) {
			return SysResult.build(50009, "有"+(count)+"个任务未关闭");
		}
		return SysResult.oK();
	}
}
