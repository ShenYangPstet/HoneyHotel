package com.photonstudio.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.google.common.collect.Maps;
import com.photonstudio.common.BindingResultMsg;
import com.photonstudio.common.ExcelUtil;
import com.photonstudio.common.enums.Status;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.Result;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Drinfo;
import com.photonstudio.pojo.Idtree;
import com.photonstudio.pojo.req.DeviceListDataReq;
import com.photonstudio.pojo.vo.DeviceListDataVO;
import com.photonstudio.service.DrinfoService;
import com.photonstudio.service.PageStyleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/zsqy/drinfo")
@Api(tags = "设备信息")
public class DrinfoController {
	@Autowired
	private DrinfoService drinfoService;
	@Autowired
	private PageStyleService pageStyleService;
	@RequestMapping("/{dBname}/findObject")//分页查询
	public SysResult findObject(@PathVariable("dBname") String dBname,String mdcode,
			           Integer userid, Integer drtypeid,Integer pageCurrent,Integer pageSize,String drname) {
		PageObject<Drinfo> pageObject=new PageObject<>();
		try {
			pageObject=drinfoService.findObject(dBname,drtypeid,mdcode,userid,pageCurrent,pageSize,drname);
			return SysResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
	}
	@RequestMapping("/{dBname}/findAll")
	public SysResult findAll(@PathVariable("dBname") String dBname,Integer drtypeid,Integer userid,Integer floorId) {
		List<Drinfo>list=new ArrayList<>();
		try {
			list=drinfoService.findAll(dBname,drtypeid,userid,floorId);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	@RequestMapping("/{dBname}/findIdtreeByDrtypeid")
	public SysResult findIdtreeByDrtypeid(@PathVariable String dBname,Integer drtypeid) {
		List<Idtree> idtrees=new ArrayList<>();
		try {
			List<Drinfo> list=drinfoService.findAll(dBname,drtypeid,null,null);
			for (Drinfo drinfo : list) {

				Idtree idtree=new Idtree();
				idtree.setId(drinfo.getDrid());
				idtree.setName(drinfo.getDrname());
				idtree.setType(2);
				idtrees.add(idtree);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
		return SysResult.oK(idtrees);
	}
	@RequestMapping("/{dBname}/findDr")
	public SysResult findDrByDrid(@PathVariable String dBname,Integer drid) {
		Drinfo drinfo=drinfoService.findDrByDrid(dBname,drid);
		return SysResult.oK(drinfo);
	}

	@RequestMapping("/{dBname}/findDrByDrIds")
	public SysResult findDrByDrIds(@PathVariable String dBname,String drname,Integer...drIds) {
		if(drIds==null||drIds.length==0)return SysResult.build(50009,"无设备");
		List<JSONObject> drinfo=drinfoService.findDrByDrIds(dBname,drname,drIds);
		return SysResult.oK(drinfo);
	}
	@RequestMapping("/{dBname}/findPicDrByType")
	public SysResult findPicDrByType(@PathVariable String dBname,Integer picId,Integer drtypeId,String drname){
		List<Integer>drinfoList=pageStyleService.getDrIdListByType(picId,drtypeId);
		if (drinfoList!=null&&drinfoList.size()>0){
			Integer[] drIds = drinfoList.toArray(new Integer[drinfoList.size()]);
			List<JSONObject> drinfo=drinfoService.findDrByDrIds(dBname,drname,drIds);
		return SysResult.oK(drinfo);
		}
		return SysResult.oK();
	}
	@RequestMapping("/{dBname}/findAllIsUser")
	public SysResult findALLIsUser(@PathVariable String dBname, Integer drtypeid) {
		List<Drinfo>list=new ArrayList<>();
		try {
			list=drinfoService.findAllIsUser(dBname,drtypeid);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}
	@RequestMapping("/{dBname}/save")//新增记录
	public SysResult saveObject(@PathVariable("dBname")String dBname,Drinfo drinfo) {
		int rows=drinfoService.saveObject(dBname,drinfo);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败");
	}
	@RequestMapping("/{dBname}/delete")
	public SysResult delete(@PathVariable("dBname")String dBname,Integer...ids) {
		if(ids==null||ids.length==0)throw new ServiceException("请选择");
		int rows=drinfoService.deleteObjectById(dBname,ids);

		if (rows==ids.length) {
				for (int i = 0; i < ids.length; i++) {
					pageStyleService.deletePageStle(String.valueOf(ids[i]));
				}
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败，结果可能已经不存在");
	}
	@RequestMapping("/{dBname}/update")
	public SysResult update(@PathVariable("dBname")String dBname,Drinfo drinfo) {
		System.out.println("修改"+drinfo);
		int rows=drinfoService.updateObject(dBname,drinfo);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "修改失败");
	}
	@RequestMapping("/{dBname}/updateUser")
	public SysResult updateUser(@PathVariable("dBname")String dBname,Integer userid,Integer...ids) {
		int rows = drinfoService.updateUser(dBname, userid, ids);

		return SysResult.oK("更新了" + rows + "条数据");

	}
	@RequestMapping("/{dBname}/removeUser")
	public SysResult removeUser(@PathVariable String dBname,Integer userid,Integer...ids) {
		int rows= drinfoService.removeUser(dBname,userid,ids);
		if (rows==ids.length) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者已经不存在");
	}
	@RequestMapping("/{dBname}/export")
	public void exportExcel(@PathVariable String dBname,HttpServletResponse response,String drname,Integer...ids) {
		if(ids!=null&&ids.length==0)ids=null;
		List<Drinfo> list=drinfoService.findObjectByids(dBname,drname,ids);

		ExcelUtil.exportExcel(list, "设备信息", dBname, Drinfo.class,dBname+"-设备信息.xls", response);

	}
	@RequestMapping("/{dBname}/import")
	public SysResult importExcel(@PathVariable String dBname,MultipartFile file) {
		List<Drinfo> list = ExcelUtil.importExcel(file, 1, 1, Drinfo.class);
		//drinfoService.importObjects(dBname, list);
			int rows=0;
			for (Drinfo drinfo : list) {
				if (drinfo.getDrid()==null||drinfo.getDrid()<1) {
					int rowss=drinfoService.saveObject(dBname, drinfo);
				}else {
					rows=drinfoService.findObjectById(dBname,drinfo.getDrid());
					if(rows==1) {
						int updateObject = drinfoService.updateObject(dBname, drinfo);
					}else {
						int saveObject = drinfoService.saveObject(dBname, drinfo);
					}
				}
			}
		pageStyleService.updatePageStyleAllDeviceInfo(dBname);
		return SysResult.oK();
	}


	@PostMapping("/findByStatList") //设备列表
	@ApiOperation("设备列表信息")
	@ApiOperationSupport(includeParameters = {
			"drname" ,"drtypeid","pageSum","pageSize"})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "drname", value = "设备名称"),
			@ApiImplicitParam(name = "drtypeid", value = "设备类型"),
			@ApiImplicitParam(name = "pageSum",value = "页数"),
			@ApiImplicitParam(name = "pageSize",value = "条数")
	})
	public Result findByStatList(@Validated @RequestBody Drinfo drinfo, BindingResult bindingResult){
		if (bindingResult.hasErrors()){
			Map<String, Object> errMap = Maps.newHashMap();
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for (ObjectError allError : allErrors) {
			 return BindingResultMsg.bindingResultMsg(bindingResult); //获取验证字段
			}
			return Result.build(Status.REQUEST_PARAMETER_ERROR.code, Status.REQUEST_PARAMETER_ERROR.message,errMap);
		}else {
			return Result.ok(drinfoService.findByListPage(drinfo));
		}

	}
	@PostMapping("/findDeviceList")
	@ApiOperation("数据列表")
    public Result<DeviceListDataVO> findDeviceList(@Validated @RequestBody DeviceListDataReq deviceListDataReq){
		return Result.ok(drinfoService.findDeviceList(deviceListDataReq));
   }
}
