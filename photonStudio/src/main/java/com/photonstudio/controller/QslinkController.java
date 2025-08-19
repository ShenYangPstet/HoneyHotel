package com.photonstudio.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.photonstudio.common.annotation.OperationLogAnnotation;
import com.photonstudio.pojo.*;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.photonstudio.common.ExcelUtil;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.service.QslinkService;


@RestController
@RequestMapping("/zsqy/link")
public class QslinkController {
	
	@Autowired
	private QslinkService qslinkService;
	
	@RequestMapping("/tj/{dBname}/findLinkTj")
	public SysResult findLinkTj( @PathVariable String dBname) {
		List<Qslinktj> list=new ArrayList<>();
		try {
			list=qslinkService.findLinkTj(dBname);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "查询失败");
	}
	
	@RequestMapping("/tj/{dBname}/insertLinkTj")
	public SysResult insertLinkTj( @PathVariable String dBname,Qslinktj qslinktj) {
		try {
			qslinkService.insertLinkTj(dBname,qslinktj);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "修改失败");
	}
	
	@RequestMapping("/tj/{dBname}/updateLinkTj")
	public SysResult updateLinkTj( @PathVariable String dBname,Qslinktj qslinktj) {
		try {
			qslinkService.updateLinkTj(dBname,qslinktj);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "修改失败");
	}
	
	@RequestMapping("/tj/{dBname}/deleteLinkTj")
	public SysResult deleteLinkTj( @PathVariable String dBname,Integer id) {
		try {
			qslinkService.deleteLinkTj(dBname,id);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "删除失败");
	}
	//导出
	@RequestMapping("/tj/{dBname}/exportQslinktj")
	public void exportQslinktj(@PathVariable String dBname,Integer [] tjId,HttpServletResponse response){
		List<Qslinktj> list=qslinkService.findBytjId(dBname,tjId);
		ExcelUtil.exportExcel(list, "条件信息", dBname, Qslinktj.class,dBname+"-联动条件.xls", response);
	}
	//导入
	@RequestMapping("/tj/{dBname}/importQslinktj")
	public SysResult importQslinktj(@PathVariable String dBname,MultipartFile file){
		List<Qslinktj> list = ExcelUtil.importExcel(file, 1, 1, Qslinktj.class);
		try {
			int rows=0;
			for (Qslinktj qslinktj : list) {
				if (qslinktj.getTjId()==null || qslinktj.getTjId()<1){
					qslinkService.insertimportQslinktj(dBname,qslinktj);
				}else {
					rows = qslinkService.countByQslinktj(dBname,qslinktj.getTjId());
					if (rows==1){
						qslinkService.updateLinkTj(dBname,qslinktj);
					}else {
						qslinkService.insertimportQslinktj(dBname,qslinktj);
					}
				}


			}
		}catch (Exception e){
			e.printStackTrace();
			return SysResult.build(50009,"保存失败");
		}
		return SysResult.oK();
	}



	@RequestMapping("/tj/{dBname}/findLinkTjinfo")
	public SysResult findLinkTjinfo( @PathVariable String dBname,Integer tjId,Integer pageCurrent,Integer pageSize) {
		PageObject<Qslinktjinfo> list = new PageObject<>();
		try {
			list=qslinkService.findLinkTjinfo(dBname,tjId,pageCurrent,pageSize);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "查询失败");
	}
	
	@RequestMapping("/tj/{dBname}/insertLinkTjinfo")
	public SysResult insertLinkTjinfo(@PathVariable String dBname,Integer tjId,Integer[] regIds,String minValue,String maxValue,Integer relation) {
		try {
			qslinkService.insertLinkTjinfo(dBname,tjId,regIds,minValue,maxValue,relation);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "查询失败");
	}
	
	@RequestMapping("/tj/{dBname}/updateLinkTjinfo")
	public SysResult updateLinkTjinfo( @PathVariable String dBname,Qslinktjinfo qslinktjinfo) {
		try {
			qslinkService.updateLinkTjinfo(dBname,qslinktjinfo);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "查询失败");
	}
	
	@RequestMapping("/tj/{dBname}/deleteLinkTjinfo")
	public SysResult deleteLinkTjinfo( @PathVariable String dBname,Integer[] ids) {
		try {
			qslinkService.deleteLinkTjinfo(dBname,ids);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "删除失败");
	}
	
	@RequestMapping("/tj/{dBname}/exportTjinfo")
	public void exportTjinfo( @PathVariable String dBname,Integer tjId,HttpServletResponse response) {
		List<Qslinktjinfo> list=new ArrayList<>();
		try {
			list=qslinkService.exportTjinfo(dBname,tjId);
			ExcelUtil.exportExcel(list, "联动条件模板", "联动条件", Qslinktjinfo.class, dBname+"-联动条件.xls", response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/tj/{dBname}/importTjinfo")
	public SysResult importTjinfo(@PathVariable String dBname,Integer tjId, MultipartFile file) {
		List<Qslinktjinfo>list =ExcelUtil.importExcel(file, 1, 1, Qslinktjinfo.class);
		try {
			for(Qslinktjinfo qslinktjinfo : list)
			{
				qslinktjinfo.setTjId(tjId);
				qslinkService.insertLinkTjinfoImport(dBname, qslinktjinfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "保存失败");
		}
		return SysResult.oK();
	}
	
	@RequestMapping("/jg/{dBname}/findLinkJg")
	public SysResult findLinkJg( @PathVariable String dBname) {
		List<Qslinkjg> list=new ArrayList<>();
		try {
			list=qslinkService.findLinkJg(dBname);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "查询失败");
	}
	
	@RequestMapping("/jg/{dBname}/insertLinkJg")
	public SysResult insertLinkJg( @PathVariable String dBname,Qslinkjg qslinkjg) {
		try {
			qslinkService.insertLinkJg(dBname,qslinkjg);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "新增失败");
	}
	
	@RequestMapping("/jg/{dBname}/updateLinkJg")
	public SysResult updateLinkJg( @PathVariable String dBname,Qslinkjg qslinkjg) {
		try {
			qslinkService.updateLinkJg(dBname,qslinkjg);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "修改失败");
	}
	
	@RequestMapping("/jg/{dBname}/deleteLinkJg")
	public SysResult deleteLinkJg( @PathVariable String dBname,Integer id) {
		try {
			qslinkService.deleteLinkJg(dBname,id);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "删除失败");
	}

	//结果导出
	@RequestMapping("/jg/{dBname}/Jgexport")
	public void Jgexport(@PathVariable String dBname,Integer [] ids,HttpServletResponse response){
		List<Qslinkjg> list=qslinkService.findByid(dBname,ids);
		ExcelUtil.exportExcel(list, "结果信息", dBname, Qslinkjg.class,dBname+"-联动结果.xls", response);
	}
	//结果导入
	@RequestMapping("/jg/{dBname}/Jgimport")
	public SysResult Jgimport(@PathVariable String dBname,MultipartFile file){
		List<Qslinkjg> list = ExcelUtil.importExcel(file, 1, 1, Qslinkjg.class);

		try {
			int rows=0;

			for (Qslinkjg qslinkjg : list) {
				if (qslinkjg.getJgId()==null || qslinkjg.getJgId()<1){
					qslinkService.insertLinkJg(dBname,qslinkjg);
				}else {
					rows = qslinkService.countByJG(dBname,qslinkjg.getJgId());
					if (rows==1){
						qslinkService.updateLinkJg(dBname,qslinkjg);
					}else {
						qslinkService.insertLinkJg(dBname,qslinkjg);
					}
				}

			}


		}catch (Exception e){
			e.printStackTrace();
			return SysResult.build(50009,"失败");
		}
		return SysResult.oK();
	}


	
	@RequestMapping("/jg/{dBname}/findLinkJginfo")
	public SysResult findLinkJginfo( @PathVariable String dBname,Integer jgId,Integer pageCurrent,Integer pageSize) {
		PageObject<Qslinkjginfo> list = new PageObject<>();
		try {
			list=qslinkService.findLinkJginfo(dBname,jgId,pageCurrent,pageSize);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "查询失败");
	}
	
	@RequestMapping("/jg/{dBname}/insertLinkJginfo")
	public SysResult insertLinkJginfo( @PathVariable String dBname,Qslinkjginfo qslinkjginfo,Integer[] regIds) {
		try {
			qslinkService.insertLinkJginfo(dBname,qslinkjginfo,regIds);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "新增失败");
	}
	
	@RequestMapping("/jg/{dBname}/updateLinkJginfo")
	public SysResult updateLinkJginfo( @PathVariable String dBname,Qslinkjginfo qslinkjginfo) {
		try {
			qslinkService.updateLinkJginfo(dBname,qslinkjginfo);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "修改失败");
	}
	
	@RequestMapping("/jg/{dBname}/deleteLinkJginfo")
	public SysResult deleteLinkJginfo( @PathVariable String dBname,Integer[] ids) {
		try {
			qslinkService.deleteLinkJginfo(dBname,ids);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "删除失败");
	}
	
	@RequestMapping("/jg/{dBname}/exportJginfo")
	public void exportJginfo( @PathVariable String dBname,Integer jgId,HttpServletResponse response) {
		List<Qslinkjginfo> list=new ArrayList<>();
		try {
			list=qslinkService.exportJginfo(dBname,jgId);
			ExcelUtil.exportExcel(list, "联动结果模板", "联动结果", Qslinkjginfo.class, dBname+"-联动结果.xls", response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/jg/{dBname}/importJginfo")
	public SysResult importJginfo(@PathVariable String dBname,Integer jgId, MultipartFile file) {
		List<Qslinkjginfo>list =ExcelUtil.importExcel(file, 1, 1, Qslinkjginfo.class);
		try {
			for(Qslinkjginfo qslinkjginfo : list)
			{
				qslinkjginfo.setJgId(jgId);
				qslinkService.insertLinkJginfoImport(dBname, qslinkjginfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "保存失败");
		}
		return SysResult.oK();
	}
	
	@RequestMapping("/rw/{dBname}/findLinkRw")
	public SysResult findLinkRw( @PathVariable String dBname,Integer tjId,Integer jgId,Integer pageCurrent,Integer pageSize) {
		PageObject<Qslinkrw> list = new PageObject<>();
		try {
			list=qslinkService.findLinkRw(dBname,tjId,jgId,pageCurrent,pageSize);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "查询失败");
	}
	
	@RequestMapping("/rw/{dBname}/insertLinkRw")
	@OperationLogAnnotation(operModul = "应急预案",operType = "联动配置-新增",Linkagetask="新增")
	public SysResult insertLinkRw( @PathVariable String dBname,Integer appid,Qslinkrw qslinkrw) {
		try {
			qslinkService.insertLinkRw(dBname,appid,qslinkrw);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "新增失败");
	}
	
	@RequestMapping("/rw/{dBname}/updateLinkRw")
	@OperationLogAnnotation(operModul = "应急预案",operType = "联动配置-修改",Linkagetask="修改")
	public SysResult updateLinkRw( @PathVariable String dBname,Qslinkrw qslinkrw) {
		try {
			qslinkService.updateLinkRw(dBname,qslinkrw);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "修改失败");
	}
	@RequestMapping("/rw/{dBname}/findLog")
	public SysResult findLog (@Parameter(description="数据库名") @PathVariable String dBname,
							  @Parameter(description="联动时间") Date sendTime,
							  @Parameter(description="页码")Integer pageCurrent,
							  @Parameter(description="页大小") Integer pageSize,
							  @Parameter(description="结果名称")String jgName,
							  @Parameter(description="任务名称")String rwName,
		                      @Parameter(description="执行状态")Integer executeState,
							  @Parameter(description = "开始时间") Date startTime,
							  @Parameter(description = "结束时间") Date endTime){
		QsLinkLog qsLinkLog = new QsLinkLog();
		PageObject<QsLinkLog> list = new PageObject<>();
		qsLinkLog.setDBname(dBname+".qs_link_log");
		qsLinkLog.setPageCurrent(pageCurrent);
		qsLinkLog.setPageSize(pageSize);
		qsLinkLog.setSendtime(sendTime);
		qsLinkLog.setJgName(jgName);
		qsLinkLog.setExecuteState(executeState);
		qsLinkLog.setRwName(rwName);
		qsLinkLog.setStartTime(startTime);
		qsLinkLog.setEndTime(endTime);
		try {
			list=qslinkService.findByPage(qsLinkLog);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "查询失败");
	}
	@RequestMapping("/rw/{dBname}/deleteLinkRw")
	@OperationLogAnnotation(operModul = "应急预按",operType = "联动配置-删除",Linkagetask ="删除")
	public SysResult deleteLinkRw( @PathVariable String dBname,Integer[] ids) {
		try {
			qslinkService.deleteLinkRw(dBname,ids);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "删除失败");
	}
	
	@RequestMapping("/rw/{dBname}/exportRw")
	public void exportRw( @PathVariable String dBname,Integer tjId,Integer jgId,HttpServletResponse response) {
		List<Qslinkrw> list=new ArrayList<>();
		try {
			list=qslinkService.exportRw(dBname,tjId,jgId);
			ExcelUtil.exportExcel(list, "联动任务模板", "联动任务", Qslinkrw.class, dBname+"-联动任务.xls", response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/rw/{dBname}/importRw")
	public SysResult importRw(@PathVariable String dBname,Integer appid,MultipartFile file) {
		List<Qslinkrw>list =ExcelUtil.importExcel(file, 1, 1, Qslinkrw.class);
		try {
			for(Qslinkrw qslinkrw : list)
			{
				qslinkService.insertLinkRw(dBname,appid, qslinkrw);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "保存失败");
		}
		return SysResult.oK();
	}
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		//转换日期
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd"); //到日
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
	}

}
