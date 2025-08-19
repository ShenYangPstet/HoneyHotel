package com.photonstudio.controller;

import com.photonstudio.common.FileUtil;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.common.vo.PicUploadResult;
import com.photonstudio.common.vo.SysResult;
import com.photonstudio.pojo.Images;
import com.photonstudio.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/zsqy/images")
public class ImagesController {
	@Autowired
	private ImagesService imagesService;

	@RequestMapping("/{dBname}/findObject")
	public SysResult findObject(@PathVariable String dBname, String imgtype, String imgname,
								Integer pageCurrent, Integer pageSize) {

		PageObject<Images> pageObject=new PageObject<>();

		try {
			pageObject=imagesService.findObjectByType(imgtype, dBname,imgname,pageCurrent, pageSize);

			return SysResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "失败");
	}
	@RequestMapping("/{dBname}/findAll")
	public SysResult findAllByImgtype(@PathVariable String dBname, String imgtype,String imgname) {
		List<Images> list=new ArrayList<>();

		try {
			list=imagesService.findAllByImgtype(dBname,imgtype,imgname);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
	}
	@RequestMapping("/{dBname}/update")
	public SysResult updateObject(@PathVariable String dBname, Images images, MultipartFile file)
	{
		String url=images.getImgurl();
		//删除图片
		if(FileUtil.deleteObject(dBname, url)==false) {
			return SysResult.build(50009, "图片删除失败");
		}
		if(null != file)
		{
			PicUploadResult uploadPic = FileUtil.uploadPic(dBname, file, images.getImgtype());
			if(uploadPic.getStatus()!=20000) {
				return SysResult.build(50009, "文件上传失败");
			}
			images.setImgurl(uploadPic.getUrl());
		}
		int rows=imagesService.updateObject(dBname,images);
		if (rows==1)
		{
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败");
	}
	@RequestMapping("/{dBname}/save")
	public SysResult saveObject(MultipartFile file
			, @PathVariable String dBname, Images images) {
		PicUploadResult uploadPic = FileUtil.uploadPic(dBname, file, images.getImgtype());
		if(uploadPic.getStatus()!=20000) {
			return SysResult.build(50009, "文件上传失败");
		}

		images.setImgurl(uploadPic.getUrl());
		int rows= imagesService.saveObject(dBname,images);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "记录保存失败");
	}
	@RequestMapping("/{dBname}/delete")
	public SysResult deleteObject(@PathVariable String dBname, Integer imgid) {
		//查询图片信息
		Images image = imagesService.findObjectById(dBname, imgid);
		String url=image.getImgurl();
		//删除图片
		if(FileUtil.deleteObject(dBname, url)==false) {
			return SysResult.build(50009, "图片删除失败");
		}
		//删除记录
		int rows=imagesService.deleteObjectById(dBname,imgid);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "记录删除失败");
	}

	//images_p处理----不用images_p表改成合并到images表
	@RequestMapping("/{dBname}/findObjectP")
	public SysResult findObjectP(@PathVariable String dBname, String imgtype, String imgname,
								 Integer pageCurrent, Integer pageSize) {

		PageObject<Images> pageObject=new PageObject<>();

		try {
//			pageObject=imagesService.findObjectPByType(imgtype, dBname,pageCurrent, pageSize);
			pageObject=imagesService.findObjectByType(imgtype, dBname, imgname, pageCurrent, pageSize);
			return SysResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(50009, "失败");
	}
	@RequestMapping("/{dBname}/findAllP")
	public SysResult findAllPByImgtype(@PathVariable String dBname, String imgtype) {
		List<Images> list=new ArrayList<>();
		try {
//			list=imagesService.findAllPByImgtype(dBname,imgtype);
			list=imagesService.findAllByImgtype(dBname,imgtype,null);
			return SysResult.oK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(50009, "失败");
		}
	}
	@RequestMapping("/{dBname}/updateP")
	public SysResult updateObjectP(@PathVariable String dBname, Images images, MultipartFile file)
	{
		String url=images.getImgurl();
		//删除图片
		if(FileUtil.deleteObject(dBname, url)==false) {
			return SysResult.build(50009, "图片删除失败");
		}
		if(null != file)
		{
			PicUploadResult uploadPic = FileUtil.uploadPic(dBname, file, images.getImgtype());
			if(uploadPic.getStatus()!=20000) {
				return SysResult.build(50009, "文件上传失败");
			}
			images.setImgurl(uploadPic.getUrl());
		}
//		int rows=imagesService.updateObjectP(dBname,images);
		int rows=imagesService.updateObject(dBname,images);
		if (rows==1)
		{
			return SysResult.oK();
		}
		return SysResult.build(50009, "失败");
	}
	@RequestMapping("/{dBname}/saveP")
	public SysResult saveObjectP(MultipartFile file
			, @PathVariable String dBname, Images images) {
		PicUploadResult uploadPic = FileUtil.uploadPic(dBname, file, images.getImgtype());
		if(uploadPic.getStatus()!=20000) {
			return SysResult.build(50009, "文件上传失败");
		}

		images.setImgurl(uploadPic.getUrl());
		//int rows= imagesService.saveObjectP(dBname,images);
		int rows= imagesService.saveObject(dBname, images);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "记录保存失败");
	}
	@RequestMapping("/{dBname}/deleteP")
	public SysResult deleteObjectP(@PathVariable String dBname, Integer imgid) {
		//查询图片信息
//		Images image = imagesService.findObjectPById(dBname, imgid);
		Images image = imagesService.findObjectById(dBname, imgid);
		String url=image.getImgurl();
		//删除图片
		if(FileUtil.deleteObject(dBname, url)==false) {
			return SysResult.build(50009, "图片删除失败");
		}
		//删除记录
//		int rows=imagesService.deleteObjectPById(dBname,imgid);
		int rows=imagesService.deleteObjectById(dBname,imgid);
		if (rows==1) {
			return SysResult.oK();
		}
		return SysResult.build(50009, "记录删除失败");
	}
}
