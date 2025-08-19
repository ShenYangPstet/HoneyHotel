package com.photonstudio.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.photonstudio.common.ExcelUtil;
import com.photonstudio.common.ObjectMapperUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.*;
import com.photonstudio.service.RegService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/zsqy/reg")
@Api(tags="变量接口")
public class RegController {
	
	@Autowired
	private RegService regService;
	
	@RequestMapping("/{dBname}/findObject")//分页查询
	public SysResult findObject(@PathVariable("dBname") String dBname,
			Integer drId,Integer pageCurrent,Integer pageSize)
	{
		PageObject<Reg> pageObject=new PageObject<>();
		try {
			System.out.println("dr_Id===="+drId);
			pageObject=regService.findObject(dBname,drId,pageCurrent,pageSize);
			return SysResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
	}
	@RequestMapping("/{dBname}/findAllByDrid")
	public SysResult findAllByDrid(@PathVariable String dBname,Integer drid,String isenergy) {
		List<Reg> list=new ArrayList<>();
		try {
			list = regService.findAllByDrid(dBname, drid,isenergy);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return SysResult.oK(list);
	}
	@RequestMapping("/{dBname}/findRegByDrListShow")
	public SysResult findRegByListShowLevel(@PathVariable String dBname,Integer drid,String...regListShowLevels) {
		List<Reg> list=regService.findRegByListShowLevel(dBname,drid,regListShowLevels);
		return SysResult.oK(list);
	}
	@RequestMapping("/{dBname}/findRegByDrid")
	public SysResult findRegByDrid(@PathVariable String dBname,Integer drid,String isenergy) {
		System.out.println("isenergy====>"+isenergy);
		List<Reg>list=regService.findRegByDrid(dBname,drid,isenergy);
		return SysResult.oK(list);
	}
	@RequestMapping("/{dBname}/findIdtreeByDrid")
	public SysResult findIdtreeByDrid(@PathVariable String dBname,Integer drid,String isenergy) {
		List<Reg>list=regService.findRegByDrid(dBname,drid,isenergy);
		List<Idtree>idtrees=new ArrayList<>();
		for (Reg reg : list) {
			Idtree idtree=new Idtree();
			idtree.setId(reg.getRegId());
			idtree.setName(reg.getRegName());
			idtree.setType(3);
			idtrees.add(idtree);
		}
		return SysResult.oK(idtrees);
	}
	@RequestMapping("/{dBname}/findQstag")
	public SysResult findQstag(@PathVariable("dBname") String dBname)
	{
		System.out.println("dBname==="+dBname);
		List<Qstag> qstagList=new ArrayList<>();
		try {
			qstagList=regService.findQstag(dBname);
			return SysResult.oK(qstagList);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
	}
	@RequestMapping("/{dBname}/findRegById")
	public SysResult findRegById (@PathVariable String dBname,Integer...ids) {
		List<Reg>list=regService.findRegById(dBname,ids);
		return SysResult.oK(list);
	}

	@PostMapping("/{dBname}/findImageRegSub")
	public SysResult findImageRegSub(@PathVariable String dBname,@RequestBody List<JSONObject> list){
		//System.out.println("元素绑定变量=="+list);
		if(list==null||list.isEmpty())return SysResult.oK();
		List<JSONObject> reglist=regService.findImageRegSub(dBname,list);
		return SysResult.oK(reglist);
	}
	@PostMapping("/{dBname}/findElementBinding")
	public SysResult findElementBinding(@PathVariable String dBname,@RequestBody JSONObject jsonObject){
		JSONObject elementObject=new JSONObject();
		List<JSONObject> elementOld=new ArrayList<>();
		JSONArray oldPara = jsonObject.getJSONArray("oldPara");
		if (!oldPara.isEmpty()){
			List<JSONObject> oldList = oldPara.toJavaList(JSONObject.class);
			elementOld=regService.findImageRegSub(dBname,oldList);
		}
		elementObject.put("oldList",elementOld);
		Map<String,String> elementNew=new HashMap<>();
		JSONArray newPara=jsonObject.getJSONArray("newPara");
		if (!newPara.isEmpty()){
			List<String> newList= newPara.toJavaList(String.class);
			elementNew = regService.findTagValueByNames(dBname,newList);
		}
		elementObject.put("newList",elementNew);
		return SysResult.oK(elementObject);
	}

	@ApiOperation("根据tagname的集合查询结果")
	@PostMapping("/{dBname}/findTagValueBinding")
	public SysResult findTagValueBinding(@PathVariable String dBname,@RequestBody List<String> list){
		if (ObjectUtil.isNotEmpty(list)){
			Map<String, String>  tagValueByNames = regService.findTagValueByNames(dBname, list);
		return SysResult.oK(tagValueByNames);
		}
		return SysResult.oK();
	}

	@RequestMapping("/{dBname}/findRegEChars")
	public SysResult findRegEChars(@PathVariable String dBname,Integer...ids){
		List<EcharsObject> list=regService.findRegEChars(dBname,ids);
		return SysResult.oK(list);
	}

	@RequestMapping("/{dBname}/save")//新增记录
	public SysResult saveObject(@PathVariable("dBname")String dBname,Reg reg,
									String regalarminfo) 
	{
		if(!StringUtils.isEmpty(regalarminfo)) {
			List<Regalarminfo>regalarminfoslist=new ArrayList<>();
			regalarminfoslist=	ObjectMapperUtil.toListObject(regalarminfo, Regalarminfo.class);
			reg.setRegalarminfoslist(regalarminfoslist);
		}
		int rows=regService.saveObject(dBname,reg);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败");
	}
	
	@RequestMapping("/{dBname}/delete")
	public SysResult delete(@PathVariable("dBname")String dBname,Integer[] ids) 
	{
		int rows=regService.deleteObjectById(dBname,ids);

		if (rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败，结果可能已经不存在");
	}
	
	@RequestMapping("/{dBname}/update")
	public SysResult update(@PathVariable("dBname")String dBname,Reg reg,String regalarminfo) {
		if(!StringUtils.isEmpty(regalarminfo)) {
			List<Regalarminfo>regalarminfoslist=new ArrayList<>();
			regalarminfoslist=ObjectMapperUtil.toListObject(regalarminfo, Regalarminfo.class);
			System.out.println("regalarminfoslist==="+regalarminfoslist);
			reg.setRegalarminfoslist(regalarminfoslist);
		}
		int rows=regService.updateObject(dBname,reg);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "修改失败");
	}
	
	@RequestMapping("/{dBname}/updateRegValue")
	public SysResult updateRegValue(@PathVariable("dBname")String dBname,Integer regId,String tagvalue) {
		int rows=regService.updateRegValue(dBname,regId,tagvalue);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "修改失败");
	}
	
	@RequestMapping("/{dBname}/export")
	public void exportExcel(@PathVariable String dBname,Integer drtypeid,String tagName,HttpServletResponse response) {
		 
			int rows=regService.getExportRowCount(dBname,drtypeid,tagName);
		
		//ExportParams params = new ExportParams("设备变量模板", "变量模板");
		//Workbook workbook = ExcelExportUtil.exportBigExcel(params, Reg.class, list);
		if(rows<20000) {
			List<Reg>list=new ArrayList<>();
			list=regService.findAllByDrtypeid(dBname, drtypeid,tagName);
			ExcelUtil.exportExcel(list, "设备变量模板", "变量模板", Reg.class, dBname+"-变量模板.xls", response);
		}else {
			EcharsObject queryParams=new EcharsObject();
			queryParams.setName(dBname);
			queryParams.setValue(String.valueOf(drtypeid));
			ExcelUtil.exportbigExcel("设备变量模板", "变量模板", queryParams, regService, Reg.class, dBname+"-变量模板.xls", response);
		}
	}
	@RequestMapping("/{dBname}/import")
	public SysResult importExcel(@PathVariable String dBname,MultipartFile file) {
		List<Reg>list =ExcelUtil.importExcel(file, 1, 1, Reg.class);
		try {
			regService.importObjects(dBname, list);
		} catch (Exception e) {
			
			e.printStackTrace();
			return SysResult.build(50009, "保存失败");
		}
		return SysResult.oK();
	}
	
	@RequestMapping("/{dBname}/exportQstag")
	public void exportQstag(@PathVariable String dBname,String tagName,HttpServletResponse response) {
		List<Qstag>list=new ArrayList<>();
		try {
			list=regService.exportQstag(dBname);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		ExcelUtil.exportExcel(list, "变量寄存器模板", "寄存器模板", Qstag.class, dBname+"-寄存器模板.xls", response);
	}
	
	@RequestMapping("/{dBname}/findRegByRegName")//分页查询  regName为空的话查询可写的变量 regname不为空 就是根据regname 模糊查询
	public SysResult findRegByRegName(@PathVariable("dBname") String dBname,
			String regName,String rw,Integer pageCurrent,Integer pageSize)
	{
		PageObject<Reg> pageObject=new PageObject<>();
		try {
			pageObject=regService.findRegByRegName(dBname,regName,rw,pageCurrent,pageSize);
			return SysResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
	}

	/**
	 * 根据设备ID查询设备变量
	 * @param dBname
	 * @param ids
	 * @return
	 */
	@GetMapping("/{dBname}/findRegBydrid")
	public SysResult findRegBydrid(@PathVariable String dBname, Integer[] ids){
		List<Reg> regList = regService.findRegBydrid(dBname,ids);
		return SysResult.oK(regList);
	}

	/**
	 * 组件绑定变量查询接口
	 * @param dBname
	 * @param regName
	 * @param drIds
	 * @return
	 */
	@PostMapping("/{dBname}/findRegByNameDrids")
	@ApiOperation("根据多个drId和变量名字模糊查询变量reg")
	public SysResult findRegByNameDrids(@PathVariable String dBname,String regName ,Integer[] drIds){
		if (ObjectUtil.isEmpty(drIds))return SysResult.build(50009,"请选择设备");
		List<Reg> regList=regService.findRegByNameDrids(dBname,regName,drIds);
		return SysResult.oK(regList);
}
}
