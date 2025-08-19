package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.ElemttypeMapper;
import com.photonstudio.pojo.Elemttype;
import com.photonstudio.service.ElemttypeService;

@Service
public class ElemttypeServiceImpl implements ElemttypeService{
	@Autowired
	private ElemttypeMapper elemttypeMapper;
	@Override
	public PageObject<Elemttype> findObject(String dBname, Integer pageCurrent, Integer pageSize) {
		if(pageCurrent==null||pageCurrent<1)pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		int rowCount=elemttypeMapper.getRowCount(dBname);
		int startIndex=(pageCurrent-1)*pageSize;
		List<Elemttype>list=new ArrayList<>();
		list=elemttypeMapper.findObject(dBname,startIndex,pageSize,null);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}
	@Override
	public int saveObject(String dBname, Elemttype elemttype) {
		int rows=0;
		try {
			rows=elemttypeMapper.insertObject(dBname,elemttype);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rows;
	}
	@Override
	public int deleteObjectById(String dBname, Integer... ids) {
		if(ids==null||ids.length==0)throw new IllegalArgumentException("请选择");
		int rows=0;
		try {
			rows=elemttypeMapper.deleteObjectById(dBname,ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}
	@Override
	public int updateObject(String dBname, Elemttype elemttype) {
		int rows=0;
		try {
			rows= elemttypeMapper.updateObject(dBname,elemttype);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}
	@Override
	public List<Elemttype> findAll(String dBname) {
		List<Elemttype> list=new ArrayList<>();
		try {
			list=elemttypeMapper.findObject(dBname, null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return list;
	}
	@Override
	public List<Elemttype> findObjectByTypeid(String dBname, Integer typeid) {
		// TODO Auto-generated method stub
		return elemttypeMapper.findObjectByTypeid(dBname,typeid);
	}
	@Override
	public Elemttype findObjectById(String dBname, Integer elemttypeid) {
		// TODO Auto-generated method stub
		return elemttypeMapper.findObjectById(dBname,elemttypeid);
	}
	
}
