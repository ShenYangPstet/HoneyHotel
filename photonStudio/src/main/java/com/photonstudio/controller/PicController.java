package com.photonstudio.controller;

import java.util.ArrayList;
import java.util.List;

import com.photonstudio.common.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Pic;
import com.photonstudio.service.PicService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/zsqy/pic")
public class PicController {
	@Autowired
	private PicService picService;

	@RequestMapping("/{dBname}/findObject")
	public SysResult findObject(@PathVariable String dBname, Integer parentid) {
		if (parentid == null) {
			parentid = 0;
		}
		List<Pic> list = new ArrayList<>();
		try {
			list = picService.findObject(dBname, parentid);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "获取失败");
	}

	@RequestMapping("/{dBname}/findObjectById")
	public SysResult findObjectById(@PathVariable String dBname, Integer picid) {
		try {
			Pic pic = picService.findObjectById(dBname, picid);
			return SysResult.oK(pic);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "获取失败");
	}

	@RequestMapping("/{dBname}/findObjectInPicmodeid") // 查询有模板的页面
	public SysResult findObjectInPicmodeid(@PathVariable String dBname, String picname) {
		List<Pic> list = picService.findObjectInPicmodeid(dBname, picname);
		return SysResult.oK(list);
	}

	@RequestMapping("/{dBname}/findAllPicNameid") // 查询所有页面，id+name
	public SysResult findAllPicNameid(@PathVariable String dBname) {
		List<Pic> list = picService.findAllPicNameid(dBname);
		return SysResult.oK(list);
	}

	@RequestMapping("/{dBname}/delete")
	public SysResult deleteObject(@PathVariable("dBname") String dBname, Integer picid) {
		try {
			picService.deleteObject(dBname, picid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return SysResult.build(50009, "删除失败");
		}

		return SysResult.oK();

	}

	@RequestMapping("/{dBname}/save")
	public SysResult saveObject(@PathVariable("dBname") String dBname, Pic pic) {
		if (ObjectUtils.isEmpty(pic.getParentid())) {
			pic.setParentid(0);
		}
		int rows = picService.saveObject(dBname, pic);
		if (rows == 1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败");
	}

	@RequestMapping("/{dBname}/update")
	public SysResult updateObject(@PathVariable("dBname") String dBname, Pic pic) {
		if (pic.getParentid() == null)
			pic.setParentid(0);
		int rows = picService.updateObject(dBname, pic);
		if (rows == 1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败");
	}

	@RequestMapping("/{dBname}/updateColor")
	public SysResult updateColor(@PathVariable("dBname") String dBname, Integer picid, String color) {
		int rows = picService.updateColor(dBname, picid, color);
		if (rows == 1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败");
	}

	@RequestMapping("/{dBname}/findAllPic")
	public SysResult findAllPic(@PathVariable String dBname) {
		List<Pic> picList = picService.findAllPic(dBname);
		List<Pic> picListResutlt = new ArrayList<>();
		for (Pic pic : picList) {
			if (pic.getParentid() == 0) {
				picListResutlt.add(pic);
			}
		}
		for (Pic pic : picListResutlt) {
			pic.setChildren(getChild(pic.getPicid(), picList));
		}
		return SysResult.oK(picListResutlt);
	}

	private List<Pic> getChild(Integer picid, List<Pic> picList) {
		// TODO Auto-generated method stub
		List<Pic> childList = new ArrayList<>();
		for (Pic pic : picList) {
			if (0 != pic.getParentid()) {
				if (pic.getParentid().equals(picid)) {
					childList.add(pic);
				}
			}
		}
		for (Pic pic : childList) {
			pic.setChildren(getChild(pic.getPicid(), picList));
		}
		return childList;
	}

	@RequestMapping("/{dBname}/findAllPicByUserid")
	public SysResult findAllPicByUserid(@PathVariable String dBname, Integer userid) {
		List<Pic> picList = picService.findAllPicByUserid(dBname, userid);
		List<Pic> picListResutlt = new ArrayList<>();
		if (picList != null) {
			for (Pic pic : picList) {
				if (pic.getParentid() == 0) {
					picListResutlt.add(pic);
				}
			}
			for (Pic pic : picListResutlt) {
				pic.setChildren(getChild(pic.getPicid(), picList));
			}
		}
		return SysResult.oK(picListResutlt);
	}
	@RequestMapping("/{dBname}/exportExcel")
	public void exportExcel(@PathVariable String dBname, HttpServletResponse response){
		List<Pic> list= null;
		try {
			list = picService.findExportPic(dBname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExcelUtil.exportExcel(list, "设备监控菜单", "设备监控菜单", Pic.class, dBname+"-设备监控菜单.xls", response);
	}

	@RequestMapping("/{dBname}/importExcel")
	public SysResult importExcel(@PathVariable String dBname, MultipartFile file){
		List<Pic>list =ExcelUtil.importExcel(file, 1, 1, Pic.class);
		try {
			picService.importObjects(dBname, list);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "保存失败");
		}
		return SysResult.oK();
	}
	/*@RequestMapping("/{dBname}/exportExcel")
	public void exportExcel(@PathVariable String dBname, HttpServletResponse response){
		List<Pic> picList = picService.findAllPic(dBname);
		List<Pic> picList0 = new ArrayList<>();
		List<Pic> picListResutlt=new ArrayList<>();
		for (Pic pic : picList) {
			if (pic.getParentid() == 0) {
				picList0.add(pic);
			}
		}
		for (Pic pic : picList0) {
			picListResutlt.addAll(getChildList(pic, picList));
		}
		System.out.println(picListResutlt);
		ExcelUtil.exportExcel(picListResutlt, "设备监控菜单", "设备监控菜单", Pic.class, dBname+"-设备监控菜单.xls", response);
	}
	private List<Pic> getChildList(Pic picFu, List<Pic> picList) {
		// TODO Auto-generated method stub
		List<Pic> childList = new ArrayList<>();
		List<Pic> picListResutlt=new ArrayList<>();
		picListResutlt.add(picFu);
		for (Pic pic : picList) {
			if (0 != pic.getParentid()) {
				if (pic.getParentid().equals(picFu.getPicid())) {
					childList.add(pic);
				}
			}
		}
		for (Pic pic : childList) {
			picListResutlt.addAll(getChildList(pic, picList));
		}
		return picListResutlt;
	}*/
}
