package com.photonstudio.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.DataInfo;
import com.photonstudio.pojo.EnergyQeury;
import com.photonstudio.pojo.Energyinfo;
import com.photonstudio.service.QueryAllenergyService;

@RestController
@RequestMapping("/zsqy/queryAllenergy")
public class QueryAllenergyController {
	
	@Autowired
	private QueryAllenergyService queryAllenergyService;

	SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
	
	@RequestMapping("/{dBname}/findSumenergy")
	public SysResult queryAllenergy(@PathVariable String dBname) {
		try
		{
			List<Energyinfo> energytypeinfoList = queryAllenergyService.queryAllenergyInfo(dBname,1);//取出总能耗类型
			System.out.println("energytypeinfoList===="+energytypeinfoList);
			List<List<EnergyQeury>> energyQeuryList = queryAllenergyService.queryAllenergy(dBname,energytypeinfoList);
			return SysResult.oK(energyQeuryList);
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	@RequestMapping("/{dBname}/findSumenergyNew")
	public SysResult queryAllenergyNew(@PathVariable String dBname) {
		try
		{
			List<Energyinfo> energytypeinfoList = queryAllenergyService.queryAllenergyInfo(dBname,1);//取出总能耗类型
			System.out.println("energytypeinfoList===="+energytypeinfoList);
			List<List<EnergyQeury>> energyQeuryList = queryAllenergyService.queryAllenergyNew(dBname,energytypeinfoList);
			return SysResult.oK(energyQeuryList);
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}

	@RequestMapping("/{dBname}/findSumenergyBytype")//type 3 日能耗 type 2 月能耗 type 1 年能耗
	public SysResult findSumenergyBytype(@PathVariable String dBname,String type,Date date) {
		Long starttime = System.currentTimeMillis();
		try
		{
			if(date == null)
			{
				date= new Date();
			}
			System.out.println("date=="+date);
			List<Energyinfo> energytypeinfoList = queryAllenergyService.queryAllenergyInfo(dBname,1);;//取出总能耗类型
			List<EnergyQeury> energyQeuryList = queryAllenergyService.findSumenergyBytype(dBname,energytypeinfoList,type,date);
			Long endtime = System.currentTimeMillis()-starttime;
			System.out.println("time is "+endtime+"毫秒");
			return SysResult.oK(energyQeuryList);
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	@RequestMapping("/{dBname}/findSumenergyBytypeNew")//type 3 日能耗 type 2 月能耗 type 1 年能耗
	public SysResult findSumenergyBytypeNew(@PathVariable String dBname,String type,Date date) {
		Long starttime = System.currentTimeMillis();
		try
		{
			if(date == null)
			{
				date= new Date();
			}
			System.out.println("date=="+date);
			List<Energyinfo> energytypeinfoList = queryAllenergyService.queryAllenergyInfo(dBname,1);;//取出总能耗类型
			List<EnergyQeury> energyQeuryList = queryAllenergyService.findSumenergyBytypeNew(dBname,energytypeinfoList,type,date);
			Long endtime = System.currentTimeMillis()-starttime;
			System.out.println("time is "+endtime+"毫秒");
			return SysResult.oK(energyQeuryList);
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	@RequestMapping("/{dBname}/findSortenergy")
	public SysResult queryAllSortenergy(@PathVariable String dBname) {
		try
		{
			List<Energyinfo> energytypeinfoList = queryAllenergyService.queryAllenergyInfo(dBname,2);//取出分类耗类型
			List<List<EnergyQeury>> energyQeuryList = queryAllenergyService.queryAllenergy(dBname,energytypeinfoList);
			return SysResult.oK(energyQeuryList);
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	@RequestMapping("/{dBname}/findSortenergyNew")
	public SysResult queryAllSortenergyNew(@PathVariable String dBname) {
		try
		{
			List<Energyinfo> energytypeinfoList = queryAllenergyService.queryAllenergyInfo(dBname,2);//取出分类耗类型
			List<List<EnergyQeury>> energyQeuryList = queryAllenergyService.queryAllenergyNew(dBname,energytypeinfoList);
			return SysResult.oK(energyQeuryList);
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}

	@RequestMapping("/{dBname}/findSortenergyBytype")//type 3 日能耗 type 2 月能耗 type 1年能耗
	public SysResult findSortenergyBytype(@PathVariable String dBname,String type,Date date) {
		try
		{
			if(date == null)
			{
				date= new Date();
			}
			List<Energyinfo> energytypeinfoList = queryAllenergyService.queryAllenergyInfo(dBname,2);//取出分类能耗类型
			List<EnergyQeury> energyQeuryList = queryAllenergyService.findSumenergyBytype(dBname,energytypeinfoList,type,date);
			return SysResult.oK(energyQeuryList);
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	@RequestMapping("/{dBname}/findSortenergyBytypeNew")//type 3 日能耗 type 2 月能耗 type 1年能耗
	public SysResult findSortenergyBytypeNew(@PathVariable String dBname,String type,Date date) {
		try
		{
			if(date == null)
			{
				date= new Date();
			}
			List<Energyinfo> energytypeinfoList = queryAllenergyService.queryAllenergyInfo(dBname,2);//取出分类能耗类型
			List<EnergyQeury> energyQeuryList = queryAllenergyService.findSumenergyBytypeNew(dBname,energytypeinfoList,type,date);
			return SysResult.oK(energyQeuryList);
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	@RequestMapping("/{dBname}/findOneEnergyInfo")
	public SysResult findOneEnergyInfo(@PathVariable String dBname,String tagname,Date date1,Date date2) {
		try
		{
			List<List<DataInfo>> dataInfoList = queryAllenergyService.findOneEnergyInfo(dBname,tagname,date1,date2);
			return SysResult.oK(dataInfoList);
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	@RequestMapping("/{dBname}/findOneEnergyInfoNew")
	public SysResult findOneEnergyInfoNew(@PathVariable String dBname,String tagname,Date date1,Date date2) {
		try
		{
			List<List<DataInfo>> dataInfoList = queryAllenergyService.findOneEnergyInfoNew(dBname,tagname,date1,date2);
			return SysResult.oK(dataInfoList);
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	@RequestMapping("/{dBname}/findzsEnergy")
	public SysResult findzsEnergy(@PathVariable String dBname,Integer energyid,Date date ,String type) {//type4时 3 日能耗 type 2 月能耗 type 1年能耗
		try {
			if(date == null)
			{
				date= new Date();
			}
			Energyinfo energyinfo = queryAllenergyService.findObjectById(dBname, energyid);
			List<EnergyQeury> energyQeuryList = queryAllenergyService.findzsEnergyBytype(dBname,energyinfo,type,date);
			return SysResult.oK(energyQeuryList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		
	}
	
	@RequestMapping("/{dBname}/findzsEnergyNew")//新能耗追溯
	public SysResult findzsEnergyNew(@PathVariable String dBname,Integer energyid,Date date ,String type) {//type4时 3 日能耗 type 2 月能耗 type 1年能耗
		try {
			if(date == null)
			{
				date= new Date();
			}
			Energyinfo energyinfo = queryAllenergyService.findObjectById(dBname, energyid);
			List<EnergyQeury> energyQeuryList = queryAllenergyService.findzsEnergyBytypeNew(dBname,energyinfo,type,date);
			return SysResult.oK(energyQeuryList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		
	}
	
	@RequestMapping("/{dBname}/findSubEnergyInfoByType")
	public SysResult findSubEnergyInfoByType(@PathVariable String dBname,String tagname,String regname,String type,Date date) {
		try
		{
			if(date == null)
			{
				date= new Date();
			}
			List<EnergyQeury> energyQeuryList = queryAllenergyService.findSubEnergyInfoByType(dBname,tagname,regname,type,date);
			return SysResult.oK(energyQeuryList);
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	@RequestMapping("/{dBname}/findRegEnergyInfoByType")//新的分项能耗统计接口
	public SysResult findRegEnergyInfoByType(@PathVariable String dBname,String tagname,String regname,String type,Date date) {
		try
		{
			if(date == null)
			{
				date= new Date();
			}
			List<EnergyQeury> energyQeuryList = queryAllenergyService.findRegEnergyInfoByType(dBname,tagname,regname,type,date);
			return SysResult.oK(energyQeuryList);
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	
	@RequestMapping("/{dBname}/findSumenergyByid")
	public SysResult findEnergyById(@PathVariable String dBname,Integer id) {
		try {
			List<Energyinfo> energytypeinfoList = new ArrayList<>();
					Energyinfo energyinfo=queryAllenergyService.findObjectById(dBname,id);
					energytypeinfoList.add(energyinfo);
			List<List<EnergyQeury>> energyQeuryList =queryAllenergyService.queryAllenergy(dBname, energytypeinfoList);
			return SysResult.oK(energyQeuryList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
					
		}
	}
	@RequestMapping("/{dBname}/findSumenergyByidtype")
	public SysResult findEnergyByType(@PathVariable String dBname,Integer id,String type,Date date) {
		try
		{
			if(date == null)
			{
				date= new Date();
			}
			System.out.println("date=="+date);
			List<Energyinfo> energytypeinfoList = new ArrayList<>();
			Energyinfo energyinfo=queryAllenergyService.findObjectById(dBname,id);
			energytypeinfoList.add(energyinfo);
			List<EnergyQeury> energyQeuryList = queryAllenergyService.findSumenergyBytype(dBname,energytypeinfoList,type,date);
			return SysResult.oK(energyQeuryList);
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	//查询项目总能耗
	@RequestMapping("/{dBname}/findAppTotalEnergy")
	public SysResult findAppTotalEnergy(@PathVariable String dBname) {
		try
		{
			Energyinfo energyinfo = queryAllenergyService.queryAppTotalenergyInfo(dBname,1,1);
			if(energyinfo==null)return SysResult.build(50009, "该项目未配置总能耗");
			Map<String,String> appTotalMap = queryAllenergyService.queryAppTotalenergy(dBname,energyinfo);
			return SysResult.oK(appTotalMap);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "查询项目总能耗失败");
		}
	}
	
	@RequestMapping("/{dBname}/findAppTotalEnergyBytype")//type 3 日能耗 type 2 月能耗 type 1 年能耗
	public SysResult findAppTotalEnergyBytype(@PathVariable String dBname,String type,Date date) {
		Long starttime = System.currentTimeMillis();
		try
		{
			if(date == null)
			{
				date= new Date();
			}
			System.out.println("date=="+date);
			List<Energyinfo> energytypeinfoList = new ArrayList<>();
			Energyinfo energyinfo = queryAllenergyService.queryAppTotalenergyInfo(dBname,1,1);
			if(energyinfo==null)return SysResult.build(50009, "该项目未配置总能耗");
			energytypeinfoList.add(energyinfo);
			List<EnergyQeury> energyQeuryList = queryAllenergyService.findSumenergyBytype(dBname,energytypeinfoList,type,date);
			Long endtime = System.currentTimeMillis()-starttime;
			System.out.println("time is "+endtime+"毫秒");
			return SysResult.oK(energyQeuryList);
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	@RequestMapping("/{dBname}/findAppTotalEnergyBytypeNew")//type 3 日能耗 type 2 月能耗 type 1 年能耗   新的首页总能耗接口
	public SysResult findAppTotalEnergyBytypeNew(@PathVariable String dBname,String type,Date date) {
		Long starttime = System.currentTimeMillis();
		try
		{
			if(date == null)
			{
				date= new Date();
			}
			System.out.println("date=="+date);
			List<Energyinfo> energytypeinfoList = new ArrayList<>();
			Energyinfo energyinfo = queryAllenergyService.queryAppTotalenergyInfo(dBname,1,1);
			if(energyinfo==null)return SysResult.build(50009, "该项目未配置总能耗");
			energytypeinfoList.add(energyinfo);
			List<EnergyQeury> energyQeuryList = queryAllenergyService.findSumenergyBytypeNew(dBname,energytypeinfoList,type,date);
			Long endtime = System.currentTimeMillis()-starttime;
			System.out.println("time is "+endtime+"毫秒");
			return SysResult.oK(energyQeuryList);
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}

    @RequestMapping("/{dBname}/findSumenergyBytypeMom")//type 3 日能耗 type 2 月能耗 type 1 年能耗  momType  momType 1是同比 2是环比
	public SysResult findSumenergyBytypeMom(@PathVariable String dBname,String type,
											 String date,String date2,Integer momType){
     Long starttime = System.currentTimeMillis();

       try {
       	 if (StringUtils.isEmpty(date)||StringUtils.isEmpty(date2)){
       	 	return SysResult.build(50009,"时间不能有一个为空");
		 }
		   List<Energyinfo> energytypeinfoList = new ArrayList<>();
		   Energyinfo energyinfo = queryAllenergyService.queryAppTotalenergyInfo(dBname,1,1);
		   if(energyinfo==null)return SysResult.build(50009, "该项目未配置总能耗");
		   energytypeinfoList.add(energyinfo);
		   List<EnergyQeury> energyQeuryList = queryAllenergyService.findSumenergyBytypeMom(dBname,energytypeinfoList,type,dateformat.parse(date),dateformat.parse(date2),momType);
		   Long endtime = System.currentTimeMillis()-starttime;
		   System.out.println("time is "+endtime+"毫秒");
		   return SysResult.oK(energyQeuryList);
	   }catch (Exception e){
       	e.printStackTrace();
       	return SysResult.build(50009,"查询失败");
	   }

	}

}
