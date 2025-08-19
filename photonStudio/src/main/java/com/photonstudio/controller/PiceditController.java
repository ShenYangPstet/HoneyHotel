package com.photonstudio.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.photonstudio.common.ExcelUtil;
import com.photonstudio.common.ObjectMapperUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Drinfo;
import com.photonstudio.pojo.Drtypeinfo;
import com.photonstudio.pojo.Elements;
import com.photonstudio.pojo.Picedit;
import com.photonstudio.service.PiceditService;

@RestController
@RequestMapping("/zsqy/picedit")
public class PiceditController {
	
	@Autowired
	private PiceditService piceditService;
	
	@RequestMapping("/{dBname}/findByPicid")
	public SysResult findPiceditByPicid(@PathVariable String dBname,Integer picid,Integer drtypeid) {
		try {
			List<Picedit> piceditListDr=piceditService.findPiceditByPicid(dBname,picid,drtypeid);
			List<Picedit> piceditListEl = piceditService.findPiceditElByPicid(dBname,picid);
			for(Picedit picedit : piceditListEl)
			{
				if("2".equals(picedit.getType()))
				{
					Integer elemtpiceditid = picedit.getId();
					List<Elements> elementsList = piceditService.findElementsListByElepicid(dBname,elemtpiceditid);
					picedit.setElements(elementsList);
				}
//				if("1".equals(picedit.getType()))
//				{
//					long t1 = System.currentTimeMillis();
//					Integer drid = picedit.getDrid();
//					Drinfo drinfo = piceditService.findDrinfoBydrid(dBname,drid);
//					picedit.setDrinfo(drinfo);
//					long t2 = System.currentTimeMillis();
//					System.out.println("耗时=="+(t2-t1));
//				}
			}
			piceditListEl.addAll(piceditListDr);
			return SysResult.oK(piceditListEl);
		}catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "根据页面id查询piceidt查询失败");
		}
	}
	
	@RequestMapping("/{dBname}/findDrStateByPicid")
	public SysResult findDrStateByPicid(@PathVariable String dBname,Integer picid) {
		
		try {
			List<Drinfo> drinfoList=piceditService.findDrStateByPicid(dBname,picid);
			return SysResult.oK(drinfoList);
		}catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "根据页面id查询页面设备状态失败");
		}
	}
	
	@RequestMapping("/{dBname}/findDrinfoRegValue")
	public SysResult findDrinfoValue(@PathVariable String dBname,Integer drtypeid,String drinfoArry) {
		
		try {
			List<Drinfo> drinfoList=piceditService.findDrinfoValue(dBname,drtypeid,drinfoArry);
			return SysResult.oK(drinfoList);
		}catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "根据页面id查询页面设备状态失败");
		}
	}
	
	@RequestMapping("/{dBname}/save")
	public SysResult saveObject(@PathVariable("dBname")String dBname,Picedit picedit) {
		int rows=piceditService.saveObject(dBname,picedit);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009,"保存失败");
	}
	
	@RequestMapping("/{dBname}/delete")
	public SysResult deleteObject(@PathVariable("dBname")String dBname,Integer... ids) {
		if(ids.length <1 )
		{
			return SysResult.build(50009,"请选中元素");
		}
		int rows=piceditService.deleteObject(dBname,ids);
		if(rows>=1) {
			return SysResult.oK();
		}
		return SysResult.build(50009,"删除失败");
	}
	
	@RequestMapping("/{dBname}/selectElements")
	public SysResult selectElements(@PathVariable("dBname")String dBname,Integer id) {
		try {
			List<Elements> elementsList = piceditService.findElementsListByElepicid(dBname,id);
			return SysResult.oK(elementsList);
		}catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "根据页面id查询Elements查询失败");
		}
	}
	
	@RequestMapping("/{dBname}/updatePicEdit")
	public SysResult updatePicEdit(@PathVariable("dBname")String dBname,String piceditupd) {
		int rows=piceditService.updatePicEdit(dBname,piceditupd);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009,"修改PicEdit失败");
	}
	
	@RequestMapping("/{dBname}/updatePicEditLngAndLat")
	public SysResult updatePicEditLngAndLat(@PathVariable("dBname")String dBname,Integer id,String lng,String lat) {
		int rows=piceditService.updatePicEditLngAndLat(dBname,id,lng,lat);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009,"修改updatePicEditLngAndLat失败");
	}
	
	@RequestMapping("/{dBname}/updateElements")
	public SysResult updateElements(@PathVariable("dBname")String dBname,Elements elements) {
		System.out.println("elements==="+elements);
		int rows=piceditService.updateElements(dBname,elements);
		if(rows>0) {
			return SysResult.oK();
		}
		return SysResult.build(50009,"修改Elements失败");
	}
	
	@RequestMapping("/{dBname}/updatePicEditDr")
	public SysResult updatePicEditDr(@PathVariable("dBname")String dBname,String piceditupd) {
		int rows=piceditService.updatePicEdit(dBname,piceditupd);
		if(rows>0) {
			return SysResult.oK();
		}
		return SysResult.build(50009,"修改PicEdit失败");
	}
	
	@RequestMapping("/{dBname}/savePicEditDr")
	public SysResult savePicEditDr(@PathVariable("dBname")String dBname,String savepiceditdr) {
		List<Picedit> piceditList = null;
		if(savepiceditdr!=null && !savepiceditdr.equals(""))
		{
			piceditList=ObjectMapperUtil.toListObject(savepiceditdr,Picedit.class);
			System.out.println("piceditList==="+piceditList);
		}
		int rows=piceditService.savePicEditDr(dBname,piceditList);
		if(rows > 0) {
			return SysResult.oK();
		}
		return SysResult.build(50009,"保存设备savePicEditDr失败");
	}
	
	@RequestMapping("/{dBname}/updatePicEditAndDrinfo")
	public SysResult updatePicEditAndDrinfo(@PathVariable("dBname")String dBname,Integer id,Integer drid,String isshowname,String drshowtype,String spid) {
		int rows=piceditService.updatePicEditAndDrinfo(dBname,id,drid,isshowname,drshowtype,spid);
		if(rows > 0) {
			return SysResult.oK();
		}
		return SysResult.build(50009,"修改PicEdit失败");
	}
	
	@RequestMapping("/{dBname}/queryDrinfoReg")
	public SysResult queryDrinfoReg(@PathVariable("dBname")String dBname,Integer picid,Integer drtypeid) {
		try {
			List<Drtypeinfo> drtypeinfoList = piceditService.queryDrinfoReg(dBname,picid,drtypeid);
			return SysResult.oK(drtypeinfoList);
		}catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询页面对于设备类型信息失败");
		}
	}
	
	@RequestMapping("/{dBname}/findImagesByPicid")
	public SysResult findImagesByPicid(@PathVariable String dBname,Integer picid) {
		
		try
		{
			String imgUrl = piceditService.findImagesByPicid(dBname,picid);
			return SysResult.oK(imgUrl);
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "查询页面model1默认URL失败");
		}
	}
	
	@RequestMapping("/{dBname}/export")
	public void exportExcel(@PathVariable String dBname,Integer picid,HttpServletResponse response) {
		List<Picedit> list=new ArrayList<>(); 
		try {
			list=piceditService.findAllPiceditByPicid(dBname,picid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		ExcelUtil.exportExcel(list, "页面编辑信息模板", "页面编辑模板", Picedit.class, dBname+"-页面编辑模板.xls", response);
	}
	
	@RequestMapping("/{dBname}/import")
	public SysResult importExcel(@PathVariable String dBname,MultipartFile file) {
		List<Picedit> list =ExcelUtil.importExcel(file, 1, 1, Picedit.class);
		try {
			piceditService.importObjects(dBname, list);
		} catch (Exception e) {
			
			e.printStackTrace();
			return SysResult.build(50009, "导入失败");
		}
		return SysResult.oK();
	}
	
	//页面复制接口
	@RequestMapping("/{dBname}/copyPicedit")
	public SysResult copyPicedit(@PathVariable String dBname,Integer picid1,Integer picid2) {
		
		try
		{
			piceditService.copyPicedit(dBname,picid1,picid2);
			return SysResult.oK();
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "页面复制失败");
		}
	}
	
	//元素复制接口
	@RequestMapping("/{dBname}/copyPiceditByids")
	public SysResult copyPiceditByids(@PathVariable String dBname,Integer picid1,Integer picid2,Integer...ids) {
		
		try
		{
			piceditService.copyPiceditByids(dBname,ids,picid1,picid2);
			return SysResult.oK();
		}catch(Exception e)
		{
			e.printStackTrace();
			return SysResult.build(50009, "元素复制失败");
		}
	}
	
	
//	//角度旋转接口
//	@RequestMapping("/{dBname}/piceditAngle")
//	public SysResult piceditAngle(@PathVariable String dBname,Integer id,Integer spintype) {
//		
//		try
//		{
//			piceditService.piceditAngle(dBname,id,spintype);//type=1 逆时针旋转  type=2顺时针旋转
//			return SysResult.oK();
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//			return SysResult.build(50009, "操作失败");
//		}
//	}
//	
//	//层级上下接口
//	@RequestMapping("/{dBname}/piceditZindex")
//	public SysResult piceditZindex(@PathVariable String dBname,Integer id,Integer indextype) {
//		
//		try
//		{
//			piceditService.piceditZindex(dBname,id,indextype);//type=1 向上一层  type=2向下一层
//			return SysResult.oK();
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//			return SysResult.build(50009, "操作失败");
//		}
//	}

}
