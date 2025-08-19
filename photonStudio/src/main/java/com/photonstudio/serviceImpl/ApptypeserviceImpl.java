package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.ApptypeMapper;
import com.photonstudio.pojo.Apptype;
import com.photonstudio.service.ApptypeService;
@Service
public class ApptypeserviceImpl implements ApptypeService{
	@Autowired
    private ApptypeMapper apptypeMapper;
	@Override
	public List<Apptype> findAll() {
		List<Apptype> list =new ArrayList<>();
		list=apptypeMapper.findObject(null, null);
		return list;
	}
	@Override
	public int saveObject(Apptype apptype) {
		int rows = 0;
		try {
			rows = apptypeMapper.insertObject(apptype);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}
	@Override
	public int deleteObjectById(Integer...ids) {
		if(ids==null||ids.length<1)throw new IllegalArgumentException("请选择");
		int rows=0;
		try {
			rows = apptypeMapper.deleteObjectById(ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败或者记录已经不存在");
		}
		return rows;
	}
	@Override
	public int updateObject(Apptype apptype) {
		int rows = 0;
		try {
			rows = apptypeMapper.updateObject(apptype);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}
	@Override
	public PageObject<Apptype> findObject(Integer pageCurrent, Integer pageSize) {
		if(pageCurrent==null||pageCurrent<1)pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		int rowCount=apptypeMapper.getRowCount();
		int startIndex=(pageCurrent-1)*pageSize;
		List<Apptype>list=new ArrayList<>();
		list=apptypeMapper.findObject(startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

}
