package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.ElemttypemodeMapper;
import com.photonstudio.pojo.Elemttypemode;
import com.photonstudio.service.ElemttypemodeService;
@Service
public class ElemttypemodeServiceImpl implements ElemttypemodeService{
	@Autowired
	private ElemttypemodeMapper elemttypemodeMapper;
	@Override
	public PageObject<Elemttypemode> findObject(String dBname, Integer elemttypeid, 
			                   Integer pageCurrent,Integer pageSize) {
		if(pageCurrent==null||pageCurrent<1)pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		int rowCount=elemttypemodeMapper.getRowCount(dBname,elemttypeid);
		int startIndex=(pageCurrent-1)*pageSize;
		List<Elemttypemode>list=new ArrayList<>();
		list=elemttypemodeMapper.findObject(dBname,elemttypeid,startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}
	@Override
	public int saveObject(String dBname, Elemttypemode elemttypemode) {
		int rows=0;
		try {
			rows=elemttypemodeMapper.insertObject(dBname,elemttypemode);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}
	@Override
	public int deleteObjectById(String dBname, Integer... ids) {
		if(ids==null||ids.length==0)throw new IllegalArgumentException("请选择");
		int rows=0;
		try {
			rows =elemttypemodeMapper.deleteObjectById(dBname,ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}
	@Override
	public int updateObject(String dBname,Elemttypemode elemttypemode) {
		int rows=0;
		try {
			rows=elemttypemodeMapper.updateObject(dBname,elemttypemode);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}
	@Override
	public List<List<Elemttypemode>> findElemttypemodeById(String dBname, Integer[] ids) {
		// TODO Auto-generated method stub
		if(ids==null||ids.length==0)throw new IllegalArgumentException("请选择模板");
		List<List<Elemttypemode>> elemttypemodeListList = new ArrayList<List<Elemttypemode>>();
		for(int i=0;i<ids.length;i++)
		{
			Integer id= ids[i];
			List<Elemttypemode> elemttypemodeList=elemttypemodeMapper.findElemttypemodeById(dBname,id);
			elemttypemodeListList.add(elemttypemodeList);
		}
		return elemttypemodeListList;
	}
	@Override
	public void synchroElemt(String dBname) {
		// TODO Auto-generated method stub
		elemttypemodeMapper.synchroElemt(dBname);
	}

}
