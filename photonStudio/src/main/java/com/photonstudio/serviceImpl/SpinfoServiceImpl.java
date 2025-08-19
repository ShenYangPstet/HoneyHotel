package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.SpinfoMapper;
import com.photonstudio.pojo.Spinfo;
import com.photonstudio.service.SpinfoService;
@Service
public class SpinfoServiceImpl implements SpinfoService{
	@Autowired
	private SpinfoMapper spinfoMapper;
	@Override
	public PageObject<Spinfo> findObject(String dBname, Integer	pageCurrent,Integer pageSize) {
		List<Spinfo> list =new ArrayList<>();
		if(pageCurrent==null||pageCurrent<1)
			pageCurrent=1;
		if(pageSize==null||pageSize<5)
			pageSize=5;
		        //基于条件查询总记录数并进行验证
				int rowCount=spinfoMapper.getRowCount(dBname);
				//依据条件查询当前页要显示的记录
				int startIndex=(pageCurrent-1)*pageSize;		
		list=spinfoMapper.findObject(dBname,startIndex,pageSize);
		PageObject<Spinfo> pageObject=new PageObject<>();
		//对查询结果进行封装并返回。
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(list);
		pageObject.setPageSize(pageSize);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageCount((rowCount-1)/pageSize+1);
		return pageObject;
	}
	@Override
	public int saveObject(String dBname, Spinfo spinfo) {
		int rows = 0;
		try {
			rows=spinfoMapper.insertObject( dBname, spinfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		
		return rows;
	}
	@Override
	public int deleteObject(String dBname, Integer... ids) {
		if(ids==null||ids.length==0)throw new IllegalArgumentException("请选择");
		int rows = 0;
		try {
			rows = spinfoMapper.deleteObjectById(dBname, ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
			
		}
		return rows;
	}
	@Override
	public int updateObject(String dBname, Spinfo spinfo) {
		int rows = 0;
		try {
			rows = spinfoMapper.updateObject(dBname,spinfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}
	@Override
	public List<Spinfo> findAll(String dBname) {
		List<Spinfo>list =new ArrayList<>();
		try {
			list=spinfoMapper.findObject(dBname, null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return list;
	}
	@Override
	public void importObjects(String dBname, List<Spinfo> list) {
		
		int rows=0;
		for (Spinfo spinfo : list) {
			if (spinfo.getId()==null||spinfo.getId()==0) {
				spinfoMapper.insertObject(dBname, spinfo);
				
			}else {
				rows=spinfoMapper.findObjectById(dBname,spinfo.getId());
				if(rows==1) {
					spinfoMapper.updateObject(dBname, spinfo);
				}else {
					spinfoMapper.insertObject(dBname, spinfo);
				}
			}
		}
	}
	@Override
	public List<Spinfo> findObjectByIds(String dBname, Integer[] ids) {
		// TODO Auto-generated method stub
		List<Spinfo>list=new ArrayList<>();
		try {
			list=spinfoMapper.findObjectByIds(dBname, ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(list);
		return list;
	}
	
}
