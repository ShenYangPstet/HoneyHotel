package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.StoreroomMapper;
import com.photonstudio.pojo.Storeroom;
import com.photonstudio.service.StoreroomService;
@Service
public class StoreroomServiceImpl implements StoreroomService {
	@Autowired
	private StoreroomMapper storeroomMapper;

	@Override
	public PageObject<Storeroom> findObject(String dBname, Integer pageCurrent, Integer pageSize) {
		if(pageCurrent==null||pageCurrent<1)pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		int startIndex=(pageCurrent-1)*pageSize;
		int rowCount=storeroomMapper.getRowCount(dBname);
		List<Storeroom>list=new ArrayList<>();
		list=storeroomMapper.findObject(dBname,startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public int saveObject(String dBname, Storeroom storeroom) {
		int rows=0;
		try {
			rows=storeroomMapper.insertObject(dBname,storeroom);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	@Override
	@Transactional
	public int deleteObjectById(String dBname, Integer... ids) {
		int rows=0;
		try {
			rows=storeroomMapper.deleteObjectById(dBname,ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int updateObject(String dBname, Storeroom storeroom) {
		int rows=0;
		try {
			rows=storeroomMapper.updateObject(dBname,storeroom);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}

	@Override
	public List<Storeroom> findAll(String dBname) {
		List<Storeroom>list=new ArrayList<>();
		try {
			list=storeroomMapper.findObject(dBname, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询错误");
		}
		return list;
	}
}
