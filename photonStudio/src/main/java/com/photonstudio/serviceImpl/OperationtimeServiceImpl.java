package com.photonstudio.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.OperationtimeMapper;
import com.photonstudio.pojo.Operationtime;
import com.photonstudio.service.OperationtimeService;

@Service
public class OperationtimeServiceImpl implements OperationtimeService{
	@Autowired
	private OperationtimeMapper operationtimeMapper;

	@Override
	public PageObject<Operationtime> findObject(String dBname, Integer drid, Integer pageCurrent, Integer pageSize) {
		if(pageCurrent==null||pageCurrent<1)pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		int startIndex=(pageCurrent-1)*pageSize;
		int rowCount=operationtimeMapper.getRowCount(dBname,drid);
		List<Operationtime>list=operationtimeMapper.findObject(dBname,drid,startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public int saveObject(String dBname, Operationtime operationtime) {
		int rows=0;
		try {
			rows=operationtimeMapper.insertObject(dBname,operationtime);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	@Override
	public int deleteObjectById(String dBname, Integer... ids) {
		if(ids==null||ids.length<1)throw new ServiceException("请选择");
		int rows=0;
		try {
			rows=operationtimeMapper.deleteObjectById(dBname,ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return rows;
	}

	@Override
	public int updateObject(String dBname, Operationtime operationtime) {
		int rows=0;
		//System.out.println(operationtime.getDrtime());
		try {
			rows=operationtimeMapper.updateObject(dBname,operationtime);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}
}
