package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.EnergyparmMapper;
import com.photonstudio.pojo.Energyparm;
import com.photonstudio.service.EnergyparmService;

@Service
public class EnergyparmServiceImpl implements EnergyparmService{
	
	@Autowired
	private EnergyparmMapper energyparmMapper;

	@Override
	public PageObject<Energyparm> findObject(String dBname, Integer pageCurrent, Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		int startIndex=(pageCurrent-1)*pageSize;
		int rowCount=energyparmMapper.getRowCount(dBname);
		List<Energyparm>list=new ArrayList<>();
		list=energyparmMapper.findObject(dBname,startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public int saveObject(String dBname, Energyparm energyparm) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=energyparmMapper.insertObject(dBname,energyparm);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	@Override
	public int deleteObjectById(String dBname, Integer id) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=energyparmMapper.deleteObjectById(dBname,id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int uptadeObject(String dBname, Energyparm energyparm) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=energyparmMapper.updateObject(dBname,energyparm);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}

}
