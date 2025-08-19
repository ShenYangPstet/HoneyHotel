package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.ElemtmodleMapper;
import com.photonstudio.pojo.Elemtmodle;
import com.photonstudio.pojo.Elemttype;
import com.photonstudio.pojo.Elemttypemode;
import com.photonstudio.service.ElemtmodleService;

@Service
public class ElemtmodleServiceImpl implements ElemtmodleService{
	
	@Autowired
	private ElemtmodleMapper elemtmodleMapper;

	@Override
	public PageObject<Elemtmodle> findObject(String dBname, Integer pageCurrent, Integer pageSize) {
		// TODO Auto-generated method stub
		if(pageCurrent==null||pageCurrent<1)pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		int rowCount=elemtmodleMapper.getRowCount(dBname);
		int startIndex=(pageCurrent-1)*pageSize;
		List<Elemtmodle>list=new ArrayList<>();
		list=elemtmodleMapper.findObject(dBname,startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public int saveObject(String dBname, Elemtmodle elemtmodle) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			if(elemtmodle.getModleLevel()==null)
			{
				elemtmodle.setModleLevel(1);
			}
			rows=elemtmodleMapper.insertObject(dBname,elemtmodle);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rows;
	}

	@Override
	public int deleteObjectById(String dBname, Integer... ids) {
		// TODO Auto-generated method stub
		if(ids==null||ids.length==0)throw new IllegalArgumentException("请选择");
		int rows=0;
		try {
			rows=elemtmodleMapper.deleteObjectById(dBname,ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int updateObject(String dBname, Elemtmodle elemtmodle) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows= elemtmodleMapper.updateObject(dBname,elemtmodle);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}

	@Override
	public List<Elemtmodle> findAll(String dBname) {
		// TODO Auto-generated method stub
		return elemtmodleMapper.findAll(dBname);
	}

	@Override
	public List<Elemtmodle> findAllTypeMode(String dBname,String elemttypename) {
		// TODO Auto-generated method stub
		List<Elemtmodle> elemtmodleList = elemtmodleMapper.findAll(dBname);
		if(null != elemtmodleList)
		{
			for(Elemtmodle elemtmodle : elemtmodleList)
			{
				Integer typeid = elemtmodle.getModleId();
				List<Elemttype> elemttypeList = elemtmodleMapper.findObjectByTypeid(dBname,typeid,elemttypename);
				if(null != elemttypeList)
				{
					for(Elemttype elemttype : elemttypeList)
					{
						Integer elemttypeid = elemttype.getElemttypeid();
						List<Elemttypemode> elemttypemodeList=elemtmodleMapper.findElemttypemodeById(dBname,elemttypeid);
						elemttype.setElemttypemodeList(elemttypemodeList);
					}
				}
				elemtmodle.setElemttypeList(elemttypeList);
			}
		}
		return elemtmodleList;
	}

}
