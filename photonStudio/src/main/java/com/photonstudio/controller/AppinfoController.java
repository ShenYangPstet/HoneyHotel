package com.photonstudio.controller;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.photonstudio.common.FileUtil;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.PicUploadResult;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Appinfo;
import com.photonstudio.service.AppinfoService;

@RestController
@RequestMapping("/zsqy/appinfo")
public class AppinfoController {
	@Autowired
	private AppinfoService appinfoService;
	
	@RequestMapping("/{dBname}/find")
	public SysResult findObject(@PathVariable String dBname,
			Integer	pageCurrent,Integer pageSize) {
		
	try {
		PageObject<Appinfo> pageObject= appinfoService.findObject(dBname,pageCurrent,pageSize);
	return SysResult.oK(pageObject);
	} catch (Exception e) {
		
		e.printStackTrace();
	}
	return SysResult.build(50009, "失败");
	}
	@RequestMapping("/{dBname}/findsumday")
	public SysResult findsumday(@PathVariable String dBname) {
		
		int sumday=0;
		try {
			sumday = appinfoService.findAppinfo(dBname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "查询失败");
		}
			return SysResult.oK(sumday);
		
		
	}
	@RequestMapping("/{dBname}/save")
	public SysResult saveObject(MultipartFile file1,MultipartFile file2,@PathVariable String dBname,
			                    Appinfo appinfo) {
		if(file1!=null) {
			
			PicUploadResult uploadFile1 = FileUtil.uploadPic(dBname, file1, "Logo");
			if(uploadFile1.getStatus()!=20000) {
				return SysResult.build(50009, "文件上传失败");
			}
			appinfo.setApplogoimg(uploadFile1.getUrl());
		}
		if(file2!=null) {
			PicUploadResult uploadFile2 = FileUtil.uploadPic(dBname, file2, "Logo");
			if(uploadFile2.getStatus()!=20000) {
				return SysResult.build(50009, "文件上传失败");
			}
			appinfo.setApppic(uploadFile2.getUrl());
		}
		
		
			int rows =appinfoService.saveObject(dBname,appinfo);
			if (rows==1) {
				return SysResult.oK();
			}
			return SysResult.build(50009, "失败");
		}
	
	@RequestMapping("/{dBname}/delete")
	public SysResult deleteObject(@PathVariable String dBname, Integer appid) {
			Appinfo appinfo=appinfoService.findObjectById(dBname,appid);
			if(appinfo==null)return SysResult.build(50009, "项目信息已经不存在");
			String pic=appinfo.getApppic();
			String url=appinfo.getApplogoimg();
			if (!FileUtil.deleteObject(dBname,url )) {
				return SysResult.build(50009, "logo图片删除失败");
			}
			if (!FileUtil.deleteObject(dBname,pic )) {
				return SysResult.build(50009, "项目图片删除失败");
			}
			
			int rows=appinfoService.deleteObject(dBname,appid);
			if (rows==1) {
				return SysResult.oK();
			}
			return SysResult.build(50009, "失败");
	}
	@RequestMapping("/{dBname}/update")
	public SysResult updateObject(@PathVariable String dBname,Appinfo appinfo,MultipartFile file1,MultipartFile file2) {
		Appinfo appinfoOld = appinfoService.findObjectById(dBname, appinfo.getAppid());
		String url1old = appinfoOld.getApplogoimg();
		String url2old = appinfoOld.getApppic();
		String url1 = appinfo.getApplogoimg();
		String url2 = appinfo.getApppic();
		if (StrUtil.isEmpty(url1.trim())) {
			FileUtil.deleteObject(dBname,url1old );
		}
		if (StrUtil.isEmpty(url2.trim())) {
			FileUtil.deleteObject(dBname,url2old );
		}
		if(file1!=null) {
			PicUploadResult uploadFile1 = FileUtil.uploadPic(dBname, file1, "Logo");
			if(uploadFile1.getStatus()!=20000) {
				return SysResult.build(50009, "文件上传失败");
			}
			appinfo.setApplogoimg(uploadFile1.getUrl());
		}
		if(file2!=null) {
			PicUploadResult uploadFile2 = FileUtil.uploadPic(dBname, file2, "Logo");
			if(uploadFile2.getStatus()!=20000) {
				return SysResult.build(50009, "文件上传失败");
			}
			appinfo.setApppic(uploadFile2.getUrl());
		}
		int rows=appinfoService.updateObject(dBname,appinfo);
		if(rows!=1) {
			return SysResult.build(50009, "失败");
		}
		return SysResult.oK();
	}
}
