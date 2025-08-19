package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.AssetstypeMapper;
import com.photonstudio.pojo.Assetstype;
import com.photonstudio.service.AssetstypeServcie;
@Service
public class AssetstypeServiceImpl implements AssetstypeServcie{
	@Autowired
	private AssetstypeMapper assetstypeMapper;
	@Override
	public PageObject<Assetstype> findObjectByName(String dBname, String assetstypename, Integer pageCurrent,
			Integer pageSize) {
		List<Assetstype> list =new ArrayList<>();
		if(pageCurrent==null||pageCurrent<1)
			pageCurrent=1;
		if(pageSize==null||pageSize<5)
			pageSize=5;
		        //基于条件查询总记录数并进行验证
				int rowCount=assetstypeMapper.getRowCount(dBname,assetstypename);
				//依据条件查询当前页要显示的记录
				int startIndex=(pageCurrent-1)*pageSize;
				list=assetstypeMapper.findObjectByName(dBname,assetstypename,startIndex,pageSize);
			return	PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}
	@Override
	public int saveObject(String dBname, Assetstype assetstype) {
		int rows=0;
		try {
			rows=assetstypeMapper.insertObject(dBname,assetstype);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}
	@Override
	public int deleteObject(String dBname,Integer...ids) {
		if (ids==null||ids.length==0) throw new IllegalArgumentException("请选择");
		int rows=0;
		try {
			rows=assetstypeMapper.deleteObjectById(dBname,ids);
		} catch (Exception e) {
             e.printStackTrace();
			 throw new ServiceException("删除失败");
		}
		return rows;
	}
	@Override
	public int updateObject(String dBname, Assetstype assetstype) {
		int rows=0;
		try {
			rows=assetstypeMapper.updateObject(dBname,assetstype);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}
	@Override
	public List<Assetstype> findAll(String dBname) {
		List<Assetstype> list=new ArrayList<>();
		try {
			list=assetstypeMapper.findObjectByName(dBname, null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return list;
	}
	@Override
	public void importObject(String dBname, List<Assetstype> list) {
		int rows=0;
		for (Assetstype assetstype : list) {
			if (assetstype.getId()==null||assetstype.getId()==0) {
				assetstypeMapper.insertObject(dBname, assetstype);
				
			}else {
				rows=assetstypeMapper.findObjectById(dBname,assetstype.getId());
				if(rows==1) {
					assetstypeMapper.updateObject(dBname, assetstype);
				}else {
					assetstypeMapper.insertObject(dBname, assetstype);
				}
			}
		}
		
	}

}
