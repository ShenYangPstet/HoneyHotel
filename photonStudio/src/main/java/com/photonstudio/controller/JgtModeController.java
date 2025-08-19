package com.photonstudio.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.DrtypeElements;
import com.photonstudio.pojo.Drtypemode;
import com.photonstudio.pojo.Jgtmode;
import com.photonstudio.service.JgtModeService;

@RestController
@RequestMapping("/zsqy/jgtmode")
public class JgtModeController {
	
	@Autowired
	private JgtModeService jgtModeService;
	
	@RequestMapping("/{dBname}/saveJgtmodeDrtype")
	public SysResult saveJgtMode(@PathVariable("dBname")String dBname,Integer drtypeid) {
		int rows=jgtModeService.saveJgtMode(dBname,drtypeid);
		if(rows>0)
		{
			return SysResult.oK();
		}
		if(rows == -1)
		{
			return SysResult.build(50009, "类型模板为空");
		}
		return SysResult.build(50009, "saveJgtMode保存失败");
	}
	
	@RequestMapping("/{dBname}/saveJgtmodeElements")
	public SysResult saveJgtmodeElements(@PathVariable("dBname")String dBname,Integer drtypeid,Integer elemttypeid,Integer jgth,Integer jgtw) {
		int rows=jgtModeService.saveJgtmodeElements(dBname,drtypeid,elemttypeid,jgth,jgtw);
		if(rows>0)
		{
			return SysResult.oK();
		}
		return SysResult.build(50009, "saveJgtmodeElements保存失败");
	}
	
	@RequestMapping("/{dBname}/findJgtBydrtypeid")
	public SysResult findJgtBydrtypeid(@PathVariable String dBname,Integer drtypeid) {
		
		try
		{
			List<Jgtmode> jgtmodeList = jgtModeService.findJgtBydrtypeid(dBname,drtypeid);
			return SysResult.oK(jgtmodeList);
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "根据类型查询结构图失败");
		}
	}
	
	@RequestMapping("/{dBname}/deleteAll")
	public SysResult deleteAll(@PathVariable String dBname,Integer drtypeid) {
		System.out.println("drtypeid=="+drtypeid);
		int rows = jgtModeService.deleteAll(dBname,drtypeid);
		if(rows>0)
		{
			return SysResult.oK();
		}
		return SysResult.build(50009, "deleteAll清空失败");
	}
	
	@RequestMapping("/{dBname}/delete")
	public SysResult delete(@PathVariable String dBname,Integer [] id,String type) {
		int rows = jgtModeService.delete(dBname,id,type);
		return SysResult.oK();
	}
	
	@RequestMapping("/{dBname}/updateJgt")
	public SysResult updateJgt(@PathVariable("dBname")String dBname,String jgtupd) {
		int rows=jgtModeService.updateJgt(dBname,jgtupd);
		if(rows>0) {
			return SysResult.oK();
		}
		return SysResult.build(50009,"修改Jgt失败");
	}
	
	@RequestMapping("/{dBname}/updateJgtmodeElements")
	public SysResult updateJgtmodeElements(@PathVariable("dBname")String dBname,DrtypeElements drtypeElements) {
		int rows=jgtModeService.updateJgtmodeElements(dBname,drtypeElements);
		if(rows>0) {
			return SysResult.oK();
		}
		return SysResult.build(50009,"修改updateJgtmodeElements失败");
	}
	
	@RequestMapping("/{dBname}/findModeBydrtypeid")
	public SysResult findModeBydrtypeid(@PathVariable String dBname,Integer drtypeid) {
		
		try
		{
			List<Drtypemode> drtypemodeList = jgtModeService.findModeBydrtypeid(dBname,drtypeid);
			return SysResult.oK(drtypemodeList);
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "根据类型查询结构图失败");
		}
	}
	
	@RequestMapping("/{dBname}/findJgtmodeElements")
	public SysResult findJgtmodeElements(@PathVariable String dBname,Integer jgtmodeid) {
		try
		{
			List<DrtypeElements> drtypeElementsList = jgtModeService.findJgtmodeElements(dBname,jgtmodeid);
			return SysResult.oK(drtypeElementsList);
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "findJgtmodeElements根据类型查询结构图元素失败");
		}
	}
	
	//结构图复制接口
	@RequestMapping("/{dBname}/copyJgt")
	public SysResult copyJgt(@PathVariable String dBname,Integer drtypeid1,Integer drtypeid2) 
	{
		try
		{
			jgtModeService.copyJgt(dBname,drtypeid1,drtypeid2);
			return SysResult.oK();
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "结构图复制失败");
		}
	}
		
	//结构图元素复制接口
	@RequestMapping("/{dBname}/copyJgtByids")
	public SysResult copyJgtByids(@PathVariable String dBname,Integer drtypeid1,Integer drtypeid2,Integer...ids) {
		try
		{
			jgtModeService.copyJgtByids(dBname,ids,drtypeid1,drtypeid2);
			return SysResult.oK();
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "元素复制失败");
		}
	}
	

}
