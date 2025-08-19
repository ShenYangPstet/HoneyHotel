package com.photonstudio.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.photonstudio.common.ExcelUtil;
import com.photonstudio.common.SpinfoMapUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.FileUploadProperteis;
import com.photonstudio.pojo.Spinfo;
import com.photonstudio.service.SpinfoService;

@RestController
@RequestMapping("/zsqy/spinfo")
public class SpinfoController {
	@Autowired
	private SpinfoService spinfoService;
	@Autowired
	private FileUploadProperteis fileUploadProperteis;

	@RequestMapping("/{dBname}/findObject")
	public SysResult findObject(@PathVariable String dBname, Integer startIndex, Integer pageSize) {

		PageObject<Spinfo> obj = spinfoService.findObject(dBname, startIndex, pageSize);
		return SysResult.oK(obj);
	}

	@RequestMapping("/{dBname}/findAll")
	public SysResult findAll(@PathVariable String dBname) {
		List<Spinfo> list = spinfoService.findAll(dBname);
		return SysResult.oK(list);
	}

	@RequestMapping("/{dBname}/save")
	public SysResult saveObject(@PathVariable String dBname, Spinfo spinfo) {

		int rows = spinfoService.saveObject(dBname, spinfo);
		if (rows == 1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败");
	}

	@RequestMapping("/{dBname}/delete")
	public SysResult deleteObject(@PathVariable String dBname, Integer... ids) {

		int rows = spinfoService.deleteObject(dBname, ids);
		if (rows == ids.length) {

			return SysResult.oK();
		}
		return SysResult.build(50009, "记录可能已经不存在");
	}

	@RequestMapping("/{dBname}/update")
	public SysResult updateObject(@PathVariable(value = "dBname") String dBname, Spinfo spinfo) {
		System.out.println(spinfo);
		int rows = spinfoService.updateObject(dBname, spinfo);
		if (rows == 1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败");

	}

	@RequestMapping("/{dBname}/export")
	public void exportExcel(@PathVariable String dBname, HttpServletResponse response) {
		List<Spinfo> list = spinfoService.findAll(dBname);
		ExcelUtil.exportExcel(list, "视频管理", dBname, Spinfo.class, dBname + "-视频管理.xls", response);
	}

	@RequestMapping("/{dBname}/import")
	public SysResult importExcel(@PathVariable String dBname, MultipartFile file) {
		List<Spinfo> list = ExcelUtil.importExcel(file, 1, 1, Spinfo.class);
		System.out.println(list);
		try {
			spinfoService.importObjects(dBname, list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("数据导入失败");
		}
		return SysResult.oK();
	}
	@RequestMapping("/{dBname}/startSP")
	public SysResult startsp(@PathVariable String dBname, Integer... ids) {
		if(ids==null||ids.length==0)return SysResult.build(50009, "请选择");
		List<Spinfo>spinfolist=spinfoService.findObjectByIds(dBname,ids);
		if(spinfolist==null||spinfolist.size()==0)return SysResult.build(50009, "视频未找到");
		for (Spinfo spinfo : spinfolist) {
			if(SpinfoMapUtil.get(spinfo.getId())!=null)continue;
			String path=fileUploadProperteis.getUploadFolder()+"nginx/temp/hls/"+spinfo.getSpType()+"_"+spinfo.getSpId();
			File file = new File(path);
			if(!file.exists()) {
				file.mkdirs();
			}
			StringBuilder spbuilder=new StringBuilder("rtsp://");
			switch (spinfo.getSpType()) {
			case 1:
				spbuilder.append(spinfo.getSpUser()).append(":").append(spinfo.getSpPassword())
				.append("@").append(spinfo.getSpIp()).append(":").append(spinfo.getSpPort())
				.append("/cam/realmonitor?channel=").append(spinfo.getSpId()).append("&subtype=0");
				break;
			case 2:
				spbuilder.append(spinfo.getSpUser()).append(":").append(spinfo.getSpPassword())
				.append("@").append(spinfo.getSpIp()).append(":").append(spinfo.getSpPort())
				.append("/Streaming/Channels/").append(spinfo.getSpId()).append("?transportmode=unicast");
			default:
				break;
			}
			System.out.println(spbuilder);
			List<String> commend = new ArrayList<String>();
			commend.add("ffmpeg");
			commend.add("-i");
			commend.add("\"" + spbuilder + "\"");
			commend.add("-c");
			commend.add("copy");
			commend.add("-f");
			commend.add("hls");
			commend.add("-hls_time");
			commend.add("2.0");
			commend.add("-hls_list_size");
			commend.add("0");
			commend.add("-hls_wrap");
			commend.add("10");
			commend.add(path+"/stream_zsqy.m3u8");
			ProcessBuilder builder;
			try {
				 builder = new ProcessBuilder(); //创建系统进程
				builder.command(commend);
				builder.start();//启动进程
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			if(builder!=null)
			SpinfoMapUtil.put(spinfo.getId(), builder);
		}
		return SysResult.oK();
	}
	@RequestMapping("/{dBname}/stopSP")
	public SysResult stopSp() {
		Runtime rt = Runtime.getRuntime();
		  try {
			rt.exec("cmd.exe /C start wmic process where name='ffmpeg.exe' call terminate");
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//关闭正在运行的cmd窗口
		  SpinfoMapUtil.removeAll();
		  return SysResult.oK();
	}
}
