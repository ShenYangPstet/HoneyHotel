package com.photonstudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Energyparm;
import com.photonstudio.service.EnergyparmService;

@RestController
@RequestMapping("/zsqy/energyparm/{dBname}")
public class EnergyparmController {
	
	@Autowired
	private EnergyparmService energyparmService;
	
	@RequestMapping("/findObject")
	public SysResult findObject(@PathVariable String dBname,
							Integer pageCurrent,Integer pageSize) {
		PageObject<Energyparm>pageObject=new PageObject<>();
		try {
			pageObject=energyparmService.findObject(dBname,pageCurrent,pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return SysResult.oK(pageObject);
	}
	
	@RequestMapping("/save")
	public SysResult saveObject(@PathVariable String dBname,Energyparm energyparm) {
		int rows=energyparmService.saveObject(dBname,energyparm);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
	@RequestMapping("/delete")
	public SysResult deleteObject(@PathVariable String dBname,Integer id) {
		int rows=energyparmService.deleteObjectById(dBname,id);
		if(rows >0) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败或者有信息已经不存在");
	}
	@RequestMapping("/update")
	public SysResult updateObject(@PathVariable String dBname,Energyparm energyparm) {
		int rows=energyparmService.uptadeObject(dBname,energyparm);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "更新失败");
	}

}
