package com.photonstudio.controller;

import com.alibaba.fastjson.JSONObject;
import com.photonstudio.common.ExcelUtil;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Regdata;
import com.photonstudio.service.RegdataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/zsqy/regdata/{dBname}")
@Api(tags = "报表历史数据")
public class RegdataController {
	@Autowired
	private RegdataService regdataService;
	@RequestMapping("/findObject")
	public SysResult findObject(@PathVariable("dBname")String dBname,
								String tagname,
								Date startTime, Date endTime) {
		
			try {
				List<Regdata>list=regdataService.findObject(dBname,tagname,startTime,endTime);
					return SysResult.oK(list);
			} catch (Exception e) {
				e.printStackTrace();
				return SysResult.build(50009, "查询失败");
			}
	}
	@RequestMapping("/findObjBytag")
	public SysResult findEchars(@PathVariable("dBname")String dBname,String regname,
								Date startTime, Date endTime,String...tagname) {
		List<Map<String, List<Regdata>>>list=regdataService.findEcharsByTagname(dBname,regname,startTime, endTime,tagname);
		return SysResult.oK(list);
	}
	@RequestMapping("/findObjBytagNew")
	public SysResult findEcharsNew(@PathVariable("dBname")String dBname,String regname,
								Date startTime, Date endTime,String[] tagname) {
		List<Map<String, List<Regdata>>> list=new ArrayList<>();
		try {
			list = regdataService.findEcharsByTagnameNew(dBname,regname,startTime, endTime,tagname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		return SysResult.oK(list);
	}
	//多选设备和变量查询报表
	@RequestMapping("/findRegDataByRegs")
	public SysResult findRegDataByRegs(@PathVariable String dBname,Date startTime, Date endTime,Integer...regIds){
		System.out.println(startTime+"===="+endTime);
		try {
			List<JSONObject>list = regdataService.findRegDataByRegs(dBname,startTime,endTime,regIds);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009,"查询失败");
		}

	}
	@RequestMapping("/findObjByDrid")
	public SysResult findEcharsByDrid(@PathVariable String dBname,Integer drid,Date startTime, Date endTime) {
		List<Map<String, List<Regdata>>> list=new ArrayList<>();
		try {
			list = regdataService.findEcharsByDrid(dBname,drid,startTime, endTime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "记录不存在");
		}
		return SysResult.oK(list);
	}

	/**
	 * 根据多个regid 查询
	 * @param dBname
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@ApiOperation("根据多个regid查询报表")
	@PostMapping("/findEcharsByRegIds")
	public SysResult findReportByRegIds(@PathVariable String dBname,Integer times,Date startTime, Date endTime,Integer...regids) {
		if (times==null)times=0;
		if (startTime==null) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);//控制时
			cal.set(Calendar.MINUTE, 0);//控制分
			cal.set(Calendar.SECOND, 0);//控制秒
			startTime=cal.getTime();
		}
		if (endTime==null)endTime=new Date();
		try
		{
			Map<String, List<String>> list=regdataService.findReportByRegIds(dBname,times,startTime, endTime,regids);
			return SysResult.oK(list);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SysResult.build(50009, "记录不存在");
		}
	}

	/**
	 * 根据多个regid 查询图标
	 * @param dBname
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@ApiOperation("根据多个regid查询曲线")
	@PostMapping("/findCurveByRegIds")
	public SysResult findCurveByRegIds(@PathVariable String dBname,Integer times,Date startTime, Date endTime,Integer...regids) {
		if (times==null)times=0;
		if (startTime==null) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);//控制时
			cal.set(Calendar.MINUTE, 0);//控制分
			cal.set(Calendar.SECOND, 0);//控制秒
			startTime=cal.getTime();
		}
		if (endTime==null)endTime=new Date();
		try
		{
			JSONObject curve=regdataService.findCurvetByRegIds(dBname,times,startTime, endTime,regids);
			return SysResult.oK(curve);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SysResult.build(50009, "记录不存在");
		}
	}

	@RequestMapping("findRegdataById")//regid查询当天历史数据
	public SysResult findRegdataById(@PathVariable String dBname,Integer...ids) {
		List<Map<String, List<Regdata>>> list=new ArrayList<>();
		try {
			list = regdataService.findRegdataById(dBname,ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SysResult.oK(list);
	}
	@RequestMapping("/save")
	public SysResult saveObject(@PathVariable("dBname")String dBname,
						@PathVariable("tagname")String tagname,Regdata regdata) {
		int rows=regdataService.saveObject(dBname+"_data",tagname,regdata);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/delete")
	public SysResult deleteObject(@PathVariable("dBname")String dBname,
						@PathVariable("tagname")String tagname,Integer...ids) {
		int rows=regdataService.deleteObjectById(dBname+"_data",tagname,ids);
		if(rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者有记录已经不存在");
	}
	@RequestMapping("/update")
	public SysResult updateObject(@PathVariable("dBname")String dBname,
			@PathVariable("tagname")String tagname,Regdata regdata) {
		int rows=regdataService.updateObject(dBname+"_data",tagname,regdata);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "更新失败");
	}
	@RequestMapping("/export")
	public void exportExcel(@PathVariable String dBname,HttpServletResponse response,String tagname,Date startTime, Date endTime) {
		List<Regdata>list=regdataService.findObject(dBname+"_data",tagname,startTime,endTime);
		ExcelUtil.exportExcel(list, "寄存器"+tagname, "寄存器", Regdata.class, "寄存器"+tagname+".xls", response);
	}
	
	@RequestMapping("/exportNew")
	public void exportNew(@PathVariable String dBname,HttpServletResponse response,String regname,
			Date startTime, Date endTime,String...tagname) {
		Map<String, List<Regdata>> map=new HashMap<String, List<Regdata>>();
		try {
			map = regdataService.exportNew(dBname,regname,startTime, endTime,tagname);
			String[] name=regname.split(",");
			List<Regdata> alllist = new ArrayList<>();
			for(String s : name)
			{
				List<Regdata> list = map.get(s);
				if (list!=null&&!list.isEmpty())
				alllist.addAll(list);
			}
			ExcelUtil.exportExcel(alllist, "数据报表", "数据", Regdata.class, "数据报表.xls", response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/findDridReport")
	public SysResult findDridReport(@PathVariable String dBname,Integer drid,Integer times,Date startTime, Date endTime) {
		Map<String, List<String>> list=regdataService.findDridReport(dBname,drid,times,startTime, endTime);
		return SysResult.oK(list);
	}
	
	@RequestMapping("/findDridReportNew")//转存历史数据到一个表中的报表
	public SysResult findDridReportNew(@PathVariable String dBname,Integer drid,Integer times,Date startTime, Date endTime) {
		try
		{
			Map<String, List<String>> list=regdataService.findDridReportNew(dBname,drid,times,startTime, endTime);
			return SysResult.oK(list);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SysResult.build(50009, "记录不存在");
		}
	}
	
	@RequestMapping("/findMorenDrid")
	public SysResult findMorenDrid(@PathVariable String dBname) {
		List<Integer> list=regdataService.findMorenDrid(dBname);
		return SysResult.oK(list);
	}
}
