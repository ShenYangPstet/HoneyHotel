package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.SubMapper;
import com.photonstudio.pojo.Sub;
import com.photonstudio.service.SubService;
@Service
public class SubServiceImpl implements SubService{
	@Autowired
	private SubMapper subMapper;
	@Override
	public List<Sub> findAll(String dBname) {
		
		return subMapper.findAll(dBname);
	}
	@Override
	public int saveObject(String dBname, Sub sub) {
		int rows=0;
		try {
			 rows=subMapper.insertObject(dBname,sub);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		
		return rows;
	}
	@Override
	public int deleteObjectById(String dBname, Integer... ids) {
		if(ids==null||ids.length==0)throw new ServiceException("请选择");
		int rows=0;
		try {
			rows=subMapper.deleteObjectById(dBname,ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败，或者已经不存在");
		}
		return rows;
	}
	@Override
	public int updateObject(String dBname, Sub sub) {
		int rows=0;
		try {
			rows=subMapper.updateObject(dBname,sub);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}
	@Override
	public PageObject<Sub> findObject(String dBname, Integer pageCurrent, Integer pageSize) {
		if(pageCurrent==null||pageCurrent<1)pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		List<Sub>list=new ArrayList<>();
		int rowCount=subMapper.getRowCount(dBname);
		int startIndex=(pageCurrent-1)*pageSize;
		list=subMapper.findObject(dBname,startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

}
