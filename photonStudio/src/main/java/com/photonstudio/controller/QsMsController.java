package com.photonstudio.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.photonstudio.common.annotation.OperationLogAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.photonstudio.common.ExcelUtil;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Qsmsdz;
import com.photonstudio.pojo.Qsmsjblog;
import com.photonstudio.pojo.Qsmsjg;
import com.photonstudio.pojo.Qsmsrw;
import com.photonstudio.service.QsMsService;

@RestController
@RequestMapping("/zsqy/ms")
public class QsMsController {
	
	@Autowired
	private QsMsService qsMsService;
	
	@RequestMapping("/rw/{dBname}/findMsRw")
	public SysResult findMsRw( @PathVariable String dBname,Integer drtypeid) {
		List<Qsmsrw> list=new ArrayList<>();
		try {
			list=qsMsService.findMsRw(dBname,drtypeid);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "查询失败");
	}
	
	@RequestMapping("/rw/{dBname}/findAllMsRw")
	public SysResult findAllMsRw( @PathVariable String dBname) {
		Map<String, List<Qsmsrw>> list= new HashMap<String, List<Qsmsrw>>();
		try {
			list=qsMsService.findAllMsRw(dBname);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "查询失败");
	}
	
	@RequestMapping("/rw/{dBname}/insertMsRw")
	@OperationLogAnnotation(operModul = "模式管理",operType = "模式编辑-模式任务新增",Modetask = "任务模式新增")
	public SysResult insertMsRw( @PathVariable String dBname,Qsmsrw qsmsrw) {
		try {
			qsMsService.insertMsRw(dBname,qsmsrw);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "新增失败");
	}
	
	@RequestMapping("/rw/{dBname}/updateMsRw")
	@OperationLogAnnotation(operModul = "模式管理",operType = "模式编辑-模式任务修改",Modetask = "任务模式修改")
	public SysResult updateMsRw( @PathVariable String dBname,Qsmsrw qsmsrw) {
		try {
			qsMsService.updateMsRw(dBname,qsmsrw);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "修改失败");
	}
	
	@RequestMapping("/rw/{dBname}/deleteMsRw")
	@OperationLogAnnotation(operModul = "模式管理",operType = "模式编辑-模式任务删除",Modetask = "任务模式删除")
	public SysResult deleteMsRw( @PathVariable String dBname,Integer id) {
		try {
			qsMsService.deleteMsRw(dBname,id);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "删除失败");
	}
	
	@RequestMapping("/rw/{dBname}/exportRw")
	public void exportRw( @PathVariable String dBname,Integer drtypeid,HttpServletResponse response) {
		List<Qsmsrw> list=new ArrayList<>();
		try {
			list=qsMsService.findMsRw(dBname,drtypeid);
			ExcelUtil.exportExcel(list, "模式任务模板", "模式任务", Qsmsrw.class, dBname+"-模式任务.xls", response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/rw/{dBname}/importRw")
	public SysResult importExcel(@PathVariable String dBname,Integer drtypeid, MultipartFile file) {
		List<Qsmsrw>list =ExcelUtil.importExcel(file, 1, 1, Qsmsrw.class);
		try {
			for(Qsmsrw qsmsrw : list)
			{
				qsmsrw.setDrtypeid(drtypeid);
				qsMsService.insertMsRw(dBname,qsmsrw);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			return SysResult.build(50009, "保存失败");
		}
		return SysResult.oK();
	}
	
	@RequestMapping("/dz/{dBname}/findMsDz")
	public SysResult findMsDz( @PathVariable String dBname,Integer rwid,Integer pageCurrent,Integer pageSize) {
		PageObject<Qsmsdz> list = new PageObject<>();
		try {
			list=qsMsService.findMsDz(dBname,rwid,pageCurrent,pageSize);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "查询失败");
	}
	
	@RequestMapping("/dz/{dBname}/insertMsDz")
	@OperationLogAnnotation(operModul = "模式管理",operType = "模式编辑-任务动作新增",Modetask = "任务动作新增")
	public SysResult insertMsDz(@PathVariable String dBname,Qsmsdz qsmsdz) {
		try {
			qsMsService.insertMsDz(dBname,qsmsdz);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "新增失败");
	}
	
	@RequestMapping("/dz/{dBname}/updateMsDz")
	@OperationLogAnnotation(operModul = "模式管理",operType = "模式编辑-任务动作修改",Modetask = "任务动作修改")
	public SysResult updateMsDz( @PathVariable String dBname,Qsmsdz qsmsdz,Integer appid) {
		try {
			qsMsService.updateMsDz(dBname,qsmsdz,appid);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "修改失败");
	}
	
	@RequestMapping("/dz/{dBname}/deleteMsDz")
	@OperationLogAnnotation(operModul = "模式管理",operType = "模式编辑-任务动作删除",Modetask = "任务动作删除")
	public SysResult deleteMsDz( @PathVariable String dBname,Integer id) {
		try {
			qsMsService.deleteMsDz(dBname,id);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "删除失败");
	}
	
	@RequestMapping("/jg/{dBname}/findMsJg")
	public SysResult findMsJg( @PathVariable String dBname,Integer dzid,Integer pageCurrent,Integer pageSize) {
		PageObject<Qsmsjg> list=new PageObject<Qsmsjg>();
		try {
			list=qsMsService.findMsJg(dBname,dzid, pageCurrent, pageSize);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "查询失败");
	}
	
	@RequestMapping("/jg/{dBname}/insertMsJg")
	@OperationLogAnnotation(operModul = "模式管理",operType = "模式编辑-配置结果新增",Modetask = "配置结果新增")
	public SysResult insertMsJg( @PathVariable String dBname,Integer dzid,Integer[] regids,String value) {
		try {
			qsMsService.insertMsJg(dBname,dzid,regids,value);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "新增失败");
	}
	
	@RequestMapping("/jg/{dBname}/updateMsJg")
	@OperationLogAnnotation(operModul = "模式管理",operType = "模式编辑-配置结果修改",Modetask = "配置结果修改")
	public SysResult updateMsJg( @PathVariable String dBname,Qsmsjg qsmsjg) {
		try {
			qsMsService.updateMsJg(dBname,qsmsjg);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "修改失败");
	}
	
	@RequestMapping("/jg/{dBname}/deleteMsJg")
	@OperationLogAnnotation(operModul = "模式管理",operType = "模式编辑-配置结果删除",Modetask = "配置结果删除")
	public SysResult deleteMsJg( @PathVariable String dBname,Integer... ids) {
		try {
			qsMsService.deleteMsJg(dBname,ids);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "删除失败");
	}
	
	@RequestMapping("/jg/{dBname}/exportJg")
	public void exportJg( @PathVariable String dBname,Integer dzid,HttpServletResponse response) {
		List<Qsmsjg> list=new ArrayList<>();
		try {
			list=qsMsService.findQsmsjg(dBname,dzid);
			ExcelUtil.exportExcel(list, "模式结果模板", "模式结果", Qsmsjg.class, dBname+"-模式结果.xls", response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/jg/{dBname}/importJg")
	public SysResult importJg(@PathVariable String dBname,Integer dzid, MultipartFile file) {
		List<Qsmsjg>list =ExcelUtil.importExcel(file, 1, 1, Qsmsjg.class);
		try {
			for(Qsmsjg qsmsjg : list)
			{
				qsmsjg.setDzid(dzid);
				qsMsService.insertQsmsjg(dBname,qsmsjg);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			return SysResult.build(50009, "保存失败");
		}
		return SysResult.oK();
	}
	
	@RequestMapping("/jb/{dBname}/insertMsJb")
	@OperationLogAnnotation(operModul = "模式管理",operType = "时间模式-新增",ModetaskTime="时间模式新增")
	public SysResult insertMsJb( @PathVariable String dBname,String[] ymds,Integer[] rwids,Integer appid) {
		try {
			qsMsService.insertMsJb(dBname,ymds,rwids,appid);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "新增失败");
	}
	
	@RequestMapping("/jb/{dBname}/findAllMsJb")
	public SysResult findAllMsJb( @PathVariable String dBname) {
		Map<String,List<Qsmsrw>> maplist = new HashMap<>();
		try {
			maplist = qsMsService.findAllMsJb(dBname);
			return SysResult.oK(maplist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "查询失败");
	}
	
	@RequestMapping("/jb/{dBname}/findMsJbLog")
	public SysResult findMsJbLog(@PathVariable String dBname,Date startTime,Date endTime,Integer pageCurrent,Integer pageSize) {
		PageObject<Qsmsjblog> list=new PageObject<Qsmsjblog>();
		try {
			list=qsMsService.findMsJbLog(dBname,startTime,endTime,pageCurrent, pageSize);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "查询失败");
	}

}
