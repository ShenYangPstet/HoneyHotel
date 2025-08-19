package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.UsergroupMapper;
import com.photonstudio.pojo.Usergroup;
import com.photonstudio.service.UsergroupService;

@Service
public class UsergroupServiceImpl implements UsergroupService{
	
	@Autowired
	private UsergroupMapper usergroupMapper;

	@Override
	public List<Usergroup> findAll(String dBname) {
		// TODO Auto-generated method stub
		return usergroupMapper.findAll(dBname);
	}

	@Override
	public PageObject<Usergroup> findObject(String dBname, Integer pageCurrent, Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		List<Usergroup>list=new ArrayList<>();
		int startIndex=(pageCurrent-1)*pageSize;
		int rowCount=usergroupMapper.getRowCount(dBname);
		list=usergroupMapper.findObject(dBname,startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public int updateObject(String dBname, Usergroup usergroup) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=usergroupMapper.updateObject(dBname,usergroup);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}

	@Override
	public int deleteObject(String dBname, Integer... ids) {
		// TODO Auto-generated method stub
		if(ids==null||ids.length==0)throw new ServiceException("请选择");
		int rows=0;
		try {
			rows=usergroupMapper.deleteObjectById(dBname,ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int saveObject(String dBname, Usergroup usergroup) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=usergroupMapper.insertObject(dBname,usergroup);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	@Override
	public Usergroup findObjectById(String dBname, Integer id) {
		Usergroup usergroup=new Usergroup();
		try {
			usergroup=usergroupMapper.findObjectById(dBname, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return usergroup;
	}

}
