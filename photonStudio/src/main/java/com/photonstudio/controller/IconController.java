package com.photonstudio.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Icon;
import com.photonstudio.service.IconService;

@Controller
@RequestMapping("/zsqy/icon")
public class IconController {
	@Autowired
	private IconService conService;
	@RequestMapping("/findAll")
	@ResponseBody
	public SysResult findAll() {
		List<Icon> list=new ArrayList<>();
		list=conService.findObject();
		return SysResult.oK(list);
		
	}
}
