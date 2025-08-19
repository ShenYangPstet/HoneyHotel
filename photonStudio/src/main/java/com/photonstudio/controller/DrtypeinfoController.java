package com.photonstudio.controller;

import cn.hutool.core.util.ObjectUtil;
import com.photonstudio.common.BindingResultMsg;
import com.photonstudio.common.enums.Status;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.Result;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Drinfo;
import com.photonstudio.pojo.Drtypeinfo;
import com.photonstudio.pojo.EcharsObject;
import com.photonstudio.pojo.Idtree;
import com.photonstudio.pojo.vo.Statusvo;
import com.photonstudio.service.DrtypeinfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/zsqy/Drtypeinfo")
@Api(tags = "设备类型文档 ")
public class DrtypeinfoController {
	@Autowired
	private DrtypeinfoService drtypeinfoService;
	@RequestMapping("/{dBname}/findObject")
	public SysResult findObjectByParentId(@PathVariable String dBname,Integer parentid) {
		if(parentid==null) {
			parentid=0;
		}
		List<Drtypeinfo>list=new ArrayList<>();
		try {
			list=drtypeinfoService.findObjectByParentId(dBname,parentid);
			
			return SysResult.oK(list);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return SysResult.build(50009, "失败");
	}
	@RequestMapping("/{dBname}/delete")
	public SysResult deleteObject(@PathVariable String dBname,Integer drtypeid) {
	int rows=drtypeinfoService.deleteObject(dBname,drtypeid);
		
			return SysResult.oK();
		
	}
	@RequestMapping("/{dBname}/save")
	public SysResult saveObject(@PathVariable String dBname,Drtypeinfo drtypeinfo) {
		if(drtypeinfo.getParentid()==null)drtypeinfo.setParentid(0);
	int rows=drtypeinfoService.saveObject(dBname,drtypeinfo);
			if (rows==1) {
				return SysResult.oK();
			}
				return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/{dBname}/update")
	public SysResult updateObject(@PathVariable String dBname,Drtypeinfo drtypeinfo) {
		if(drtypeinfo.getParentid()==null)drtypeinfo.setParentid(0);
		int rows=drtypeinfoService.updateObject(dBname,drtypeinfo);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "修改失败");
	}
	@RequestMapping("/{dBname}/findIdtree")
	public SysResult findIdtree(@PathVariable String dBname,Integer id) {
		List<Idtree> list=new ArrayList<>();
		try {
			list = drtypeinfoService.findIdtree(dBname,id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50008, "查询失败");
		}
		
		return SysResult.oK(list);
	}
	@RequestMapping("/{dBname}/findAllDrtypeAndDr")
	public SysResult findAllDrtypeAndDr(@PathVariable String dBname) {
		try
		{
			List<Drtypeinfo> drtypeinfoList = drtypeinfoService.findAllDrtypeAndDr(dBname,0);
			return SysResult.oK(drtypeinfoList);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return SysResult.build(50009, "失败");
	}
	@RequestMapping("/{dBname}/findAllDrtypeByAlarm")
	public SysResult findAllDrtypeByAlarm(@PathVariable String dBname) {
		try
		{
			List<Drtypeinfo> drtypeinfoList = drtypeinfoService.findAllDrtypeAndDr(dBname,0);
			for(Drtypeinfo drtypeinfo : drtypeinfoList)
			{
				List<Drtypeinfo> drtypeinfoChList = drtypeinfo.getDrtypeinfoList();
				if(drtypeinfoChList.size()==0)
				{
					drtypeinfo.setDrtypeinfoList(null);
				}
				else
				{
					for(Drtypeinfo drtypeinfo2 : drtypeinfoChList)
					{
						List<Drtypeinfo> drtypeinfoChList2 = drtypeinfo2.getDrtypeinfoList();
						if(drtypeinfoChList2.size()==0)
						{
							drtypeinfo2.setDrtypeinfoList(null);
						}
						else
						{
							for(Drtypeinfo drtypeinfo3 : drtypeinfoChList2)
							{
								List<Drtypeinfo> drtypeinfoChList3 = drtypeinfo3.getDrtypeinfoList();
								if(drtypeinfoChList3.size()==0)
								{
									drtypeinfo3.setDrtypeinfoList(null);
								}
							}
						}
					}
				}
			}
			return SysResult.oK(drtypeinfoList);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return SysResult.build(50009, "失败");
	}
	@RequestMapping("/{dBname}/iscustom")//自定义
	public SysResult findObject(@PathVariable String dBname,Integer pageCurrent,
											Integer pageSize) {
		PageObject<Drtypeinfo>pageObject=new PageObject<>();
		try {
			pageObject=drtypeinfoService.findiscustom(dBname,pageCurrent,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return SysResult.oK(pageObject);
	}
	@RequestMapping("/{dBname}/findAllByIscustom")
	public SysResult findAllByIscustom(@PathVariable String dBname,Integer iscustomType) {
		List<Drtypeinfo>list=drtypeinfoService.findAllByIscustom(dBname,iscustomType);
		return SysResult.oK(list);
	}
	@RequestMapping("/{dBname}/findAllDrtypevo")
	public SysResult findAllDrtype(@PathVariable String dBname,Integer drtypeid) {
		List<Drtypeinfo>list=new ArrayList<>();
		list=drtypeinfoService.findAllDrtypevo(dBname,drtypeid);
		return SysResult.oK(list);
	}
	@RequestMapping("/{dBname}/findAllDrtypeJgt")
	public SysResult findAllDrtypeJgt(@PathVariable String dBname) {
		try
		{
			List<Drtypeinfo> drtypeinfoList = drtypeinfoService.findAllDrtypeJgt(dBname);
			for(Drtypeinfo drtypeinfo : drtypeinfoList)
			{
				List<Drtypeinfo> drtypeinfoChList = drtypeinfo.getDrtypeinfoList();
				if(drtypeinfoChList.size()==0)
				{
					drtypeinfo.setDrtypeinfoList(null);
				}
				else
				{
					for(Drtypeinfo drtypeinfo2 : drtypeinfoChList)
					{
						List<Drtypeinfo> drtypeinfoChList2 = drtypeinfo2.getDrtypeinfoList();
						if(drtypeinfoChList2.size()==0)
						{
							drtypeinfo2.setDrtypeinfoList(null);
						}
						else
						{
							for(Drtypeinfo drtypeinfo3 : drtypeinfoChList2)
							{
								List<Drtypeinfo> drtypeinfoChList3 = drtypeinfo3.getDrtypeinfoList();
								if(drtypeinfoChList3.size()==0)
								{
									drtypeinfo3.setDrtypeinfoList(null);
								}
							}
						}
					}
				}
			}
			return SysResult.oK(drtypeinfoList);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return SysResult.build(50009, "失败");
	}
	@RequestMapping("/{dBname}/findDrStateByDrtype")
	public SysResult findDrStateByDrtype(@PathVariable String dBname,Integer drtypeid) {
		List<EcharsObject> list;
		try {
			list = drtypeinfoService.findDrStateByDrtype(dBname,drtypeid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		return SysResult.oK(list);
	}
	
	@RequestMapping("/{dBname}/findDrinfoByType")
	public SysResult findDrinfoByType(@PathVariable String dBname,Integer drtypeid,String drname) {
		try
		{
			List<Drinfo> drinfoList = drtypeinfoService.findDrinfoByType(dBname,drtypeid,drname);
			return SysResult.oK(drinfoList);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return SysResult.build(50009, "失败");
	}
	@RequestMapping("/{dBname}/findDrtypeinfo")
	public SysResult findDrtypeinfoByDrid(@PathVariable String dBname,Integer drid) {
		Drtypeinfo drtypeinfo=drtypeinfoService.findDrtypeinfoByDrid(dBname,drid);
		return SysResult.oK(drtypeinfo);
	}
	@RequestMapping("/{dBname}/findWorkorderByDrtype")
	public SysResult findWorkorderByDrtype(@PathVariable String dBname) {
		Map<String, Double>map=new HashMap<>();
		try {
			map=drtypeinfoService.findWorkorderByDrtype(dBname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		return SysResult.oK(map);
	}
	
	@RequestMapping("/{dBname}/moveDrtypeAndModle")
	public SysResult moveDrtypeAndModle(@PathVariable String dBname,String drtypeids) {
		System.out.println("drtypeids=="+drtypeids);
		if(drtypeids == null || drtypeids.equals(""))
		{
			return SysResult.build(50009, "设备类型为空，请勾选");
		}
		try {
			drtypeinfoService.moveDrtypeAndModle(dBname,drtypeids);
			return SysResult.oK();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "保存失败");
		}
	}
	
	@RequestMapping("/{dBname}/findDrtypeinfoBytypeid")
	public SysResult findDrtypeinfoBytypeid(@PathVariable String dBname,Integer drtypeid) {
		try {
			Drtypeinfo drtypeinfo = drtypeinfoService.findDrtypeinfoBytypeid(dBname,drtypeid);
			return SysResult.oK(drtypeinfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	
	@RequestMapping("/{dBname}/findBasisDrtype")
	public SysResult findBasisDrtype(@PathVariable String dBname) {
		try {
			List<Drtypeinfo> drtypeinfolist = drtypeinfoService.findBasisDrtype(dBname);
			return SysResult.oK(drtypeinfolist);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}

	@PostMapping("/findAllStatusVos")
	@ApiOperation("新设备在离线")
    public Result  findAllStatusVos(@Validated @RequestBody Statusvo statusvo, BindingResult bindingResult, HttpServletRequest request){
		String database = request.getHeader("database");
		if (ObjectUtil.isEmpty(database)) return Result.build(Status.REQUEST_PARAMETER_ERROR.code,"数据库不能为空");
		if (bindingResult.hasErrors()){
			return BindingResultMsg.bindingResultMsg(bindingResult);
		}else {
		   return Result.ok(drtypeinfoService.findAllStatusVos(database,statusvo.getIntegerList()));
		}
   }
}
