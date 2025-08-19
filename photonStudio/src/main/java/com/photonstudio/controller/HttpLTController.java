package com.photonstudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Appmanager;
import com.photonstudio.service.AppmanagerService;

@RestController
@RequestMapping("/zsqy/lt/{dBname}")
public class HttpLTController {
	
	@Autowired
	private AppmanagerService appmanagerService;
	@Autowired
	private RestTemplate restTemplate;
	@RequestMapping("/chat")
	public SysResult httpLT(Integer appid, String useltid,String contactid,String msg) {
		if(StringUtils.isEmpty(contactid))return SysResult.build(50009, "目标不能为空");
		if(StringUtils.isEmpty(msg))return SysResult.build(50009, "消息不能为空");
		System.out.println("useltid==="+useltid);
		Appmanager appmanager=appmanagerService.findObjectByAppid(appid);
		String url="http://"+appmanager.getIpaddr()+":"+appmanager.getAppport()+"/caiji/lt/"+useltid+"/"+contactid+"/"+msg;
		try {
			SysResult result = restTemplate.getForObject(url, SysResult.class);
			return result;
		} catch (RestClientException e) {
			
			e.printStackTrace();
			return SysResult.build(50009, "发送失败");
	}
	}
	@RequestMapping("/findLTuser")
	public SysResult httpltcx(Integer appid) {
		Appmanager appmanager=appmanagerService.findObjectByAppid(appid);
		String url="http://"+appmanager.getIpaddr()+":"+appmanager.getAppport()+"/caiji/findLTuser";
		try {
			SysResult result = restTemplate.getForObject(url, SysResult.class);
			return result;
		} catch (RestClientException e) {
			
			e.printStackTrace();
			return SysResult.build(50009, "发送失败");
	}
	}
	
}
