package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.ManufacturerMapper;
import com.photonstudio.pojo.Manufacturer;
import com.photonstudio.service.ManufacturerServcie;
@Service
public class ManufacturerServiceImpl implements ManufacturerServcie {
	@Autowired
	private ManufacturerMapper manufacturerMapper;

	@Override
	public PageObject<Manufacturer> findObject(String dBname, String manufactorname, Integer pageCurrent,
			Integer pageSize) {
		// TODO Auto-generated method stub
		List<Manufacturer> list=new ArrayList<>();
		//参数验证
		if(pageCurrent==null||pageCurrent<1)pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		int rowCount=manufacturerMapper.getRowCount(dBname,manufactorname);
		int startIndex=(pageCurrent-1)*pageSize;
		list=manufacturerMapper.findObject(manufactorname,dBname,startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public List<Manufacturer> findAll(String dBname) {
		// TODO Auto-generated method stub
		List<Manufacturer>list=new ArrayList<>();
		try {
			list=manufacturerMapper.findObject(null, dBname, null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return list;
	}

	@Override
	public int saveObject(String dBname, Manufacturer manufacturer) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=manufacturerMapper.insertObject(dBname,manufacturer);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		
		return rows;
	}

	@Override
	public int deleteObjectById(String dBname, Integer[] ids) {
		// TODO Auto-generated method stub
		if(ids==null||ids.length==0)
			throw new IllegalArgumentException("请先选择");
			//执行删除操作
			int rows=0;
			try{
			 rows=manufacturerMapper.deleteObjectById(dBname,ids);
			}catch(Throwable e){
			 e.printStackTrace();
			 
			 throw new ServiceException("删除失败");
			}
		return rows;
	}

	@Override
	public int updateObject(String dBname, Manufacturer manufacturer) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=manufacturerMapper.updateObject(dBname,manufacturer);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}
}
