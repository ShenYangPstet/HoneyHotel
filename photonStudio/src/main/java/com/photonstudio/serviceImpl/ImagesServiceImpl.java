package com.photonstudio.serviceImpl;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.ImagesMapper;
import com.photonstudio.pojo.Images;
import com.photonstudio.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImagesServiceImpl implements ImagesService {
	@Autowired
	private ImagesMapper imagesMapper;
	@Override
	public PageObject<Images> findObjectByType(String imgtype, String dBname, String imgname, Integer pageCurrent,
											   Integer pageSize) {
		List<Images> list=new ArrayList<>();
		//参数验证
		if(pageCurrent==null||pageCurrent<1)
			pageCurrent=1;
		if(pageSize==null||pageSize<5)
			pageSize=5;
		///基于条件查询总记录数并进行验证
		int rowCount=imagesMapper.getRowCount(dBname,imgtype,imgname);
		//依据条件查询当前页要显示的记录
		int startIndex=(pageCurrent-1)*pageSize;
		list=imagesMapper.findObjectByType(imgtype,dBname,imgname,startIndex,pageSize);
		/*
		 * PageObject<Images> pageObject=new PageObject<>();
		 * pageObject.setRowCount(rowCount); pageObject.setPageCurrent(pageCurrent);
		 * pageObject.setPageSize(pageSize); pageObject.setRecords(list);
		 * pageObject.setPageCount((rowCount-1)/pageSize+1);
		 */
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);

	}
	@Override
	public int deleteObjectById(String dBname, Integer imgid) {
		if(imgid==null||imgid<0)throw new IllegalAccessError("参数id无效,请选择");
		int rows=0;
		try {
			rows = imagesMapper.deleteObjectById(imgid, dBname);
		} catch (Exception e) {

			e.printStackTrace();
			throw new ServiceException("记录删除失败");
		}

		return rows;
	}
	@Override
	public int updateObject(String dBname, Images images) {
		int rows = 0;
		try {
			rows = imagesMapper.updateObject(dBname, images);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("记录更新失败");
		}

		return rows;
	}
	@Override
	public int saveObject(String dBname, Images images) {
		int rows=0;
		try {
			if(dBname.equals("zsqy_v2"))
			{
				int id  = (int)((Math.random()*9+1)*100000);
				images.setImgid(id);
				rows = imagesMapper.insertObjcet(dBname, images);
			}
			else
			{
				int id  = (int)((Math.random()*9+1)*10000);
				images.setImgid(id);
				rows = imagesMapper.insertObjcet(dBname, images);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("记录保存失败");
		}
		return rows;
	}
	@Override
	public List<Images> findAllByImgtype(String dBname, String imgtype,String imgname) {
		List<Images> list=imagesMapper.findObjectByType(imgtype, dBname, imgname, null, null);
		return list;
	}
	@Override
	public Images findObjectById(String dBname, Integer imgid) {
		Images images=imagesMapper.findObjectById(dBname, imgid);
		return images;
	}
	@Override
	public PageObject<Images> findObjectPByType(String imgtype, String dBname, Integer pageCurrent, Integer pageSize) {
		// TODO Auto-generated method stub
		List<Images> list=new ArrayList<>();
		//参数验证
		if(pageCurrent==null||pageCurrent<1)
			pageCurrent=1;
		if(pageSize==null||pageSize<5)
			pageSize=5;
		///基于条件查询总记录数并进行验证
		int rowCount=imagesMapper.getRowCountP(dBname,imgtype);
		//依据条件查询当前页要显示的记录
		int startIndex=(pageCurrent-1)*pageSize;
		list=imagesMapper.findObjectPByType(imgtype,dBname,startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}
	@Override
	public List<Images> findAllPByImgtype(String dBname, String imgtype) {
		// TODO Auto-generated method stub
		List<Images> list=imagesMapper.findObjectPByType(imgtype, dBname, null, null);
		return list;
	}
	@Override
	public int updateObjectP(String dBname, Images images) {
		// TODO Auto-generated method stub
		int rows = 0;
		try {
			rows = imagesMapper.updateObjectP(dBname, images);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("记录更新失败");
		}

		return rows;
	}
	@Override
	public int saveObjectP(String dBname, Images images) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows = imagesMapper.insertObjcetP(dBname, images);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("记录保存失败");
		}
		return rows;
	}
	@Override
	public Images findObjectPById(String dBname, Integer imgid) {
		// TODO Auto-generated method stub
		Images images=imagesMapper.findObjectPById(dBname, imgid);
		return images;
	}
	@Override
	public int deleteObjectPById(String dBname, Integer imgid) {
		// TODO Auto-generated method stub
		if(imgid==null||imgid<0)throw new IllegalAccessError("参数id无效,请选择");
		int rows=0;
		try {
			rows = imagesMapper.deleteObjectPById(imgid, dBname);
		} catch (Exception e) {

			e.printStackTrace();
			throw new ServiceException("记录删除失败");
		}

		return rows;
	}

}
