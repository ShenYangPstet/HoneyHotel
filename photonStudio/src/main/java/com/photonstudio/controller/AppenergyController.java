package com.photonstudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.AppenergyData;
import com.photonstudio.service.AppenergyService;

import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/zsqy/appenergy")
@Validated
public class AppenergyController {
	
	@Autowired
	private AppenergyService appenergyService;
	
	
	@RequestMapping("/queryAppenergyData")
	public SysResult queryAppenergyData(@NotEmpty(message = "{required}") Integer[] appids)
	{
		try
		{
			AppenergyData appenergyData = appenergyService.queryAppenergyData(appids);
			return SysResult.oK(appenergyData);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
	}

}
