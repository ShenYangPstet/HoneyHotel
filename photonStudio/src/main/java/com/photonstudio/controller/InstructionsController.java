package com.photonstudio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Instructions;
import com.photonstudio.service.InstructionsService;

@RestController
@RequestMapping("/zsqy/instructions")
public class InstructionsController {
	@Autowired
	private InstructionsService instructionsService;
	@RequestMapping("/{dBname}/findObject")
	public SysResult findObject(@PathVariable String dBname,
								Integer instructionsTypeid,
								Integer pageCurrent,Integer pageSize) {
		PageObject<Instructions> pageObject=new PageObject<>();
		try {
			pageObject=instructionsService.findObject(
					dBname,instructionsTypeid,pageCurrent,pageSize);
			return SysResult.oK(pageObject);
		} catch (Exception e) {
			
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
		
	}
	@RequestMapping("/{dBname}/findAll")
	public SysResult findAll(@PathVariable String dBname,Integer instructionsTypeid) {
		
			List<Instructions>list=instructionsService.findAllByInstructionsTypeid(dBname,instructionsTypeid);
			return SysResult.oK(list);
		
	}
	@RequestMapping("/{dBname}/save")
	public SysResult saveObject(@PathVariable String dBname,
			Instructions instructions,MultipartFile file) {
	
		int rows=instructionsService.saveObject(dBname,instructions,file);
		if(rows==1) {
			return SysResult.oK(instructions.getFilepath());
		}
		return SysResult.build(50009, "记录保存失败");
	}
	@RequestMapping("/{dBname}/delete")
	public SysResult delete(@PathVariable String dBname,Integer id) {
		
		int rows=instructionsService.deleteObjectById(dBname,id);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "删除失败");
	}
	@RequestMapping("/{dBname}/update")
	public SysResult update(@PathVariable String dBname,MultipartFile file,
											Instructions instructions) {
		int rows=instructionsService.updateObject(dBname, file,instructions);
		if(rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "保存失败");
	}
}
