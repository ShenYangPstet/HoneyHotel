package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.EnergyinfoMapper;
import com.photonstudio.pojo.Energyinfo;
import com.photonstudio.service.EnergyinfoService;
@Service
public class EnergyinfoServiceImpl implements EnergyinfoService{
	@Autowired
	private EnergyinfoMapper energyinfoMapper;

	@Override
	public PageObject<Energyinfo> findObject(String dBname, Integer pageCurrent, Integer pageSize) {
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		int startIndex=(pageCurrent-1)*pageSize;
		int rowCount=energyinfoMapper.getRowCount(dBname);
		List<Energyinfo>list=new ArrayList<>();
		list=energyinfoMapper.findObject(dBname,startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public int saveObject(String dBname, Energyinfo energyinfo) {
		
		int rows=0;
		try {
			rows=energyinfoMapper.insertObject(dBname,energyinfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	@Override
	public int deleteObjectById(String dBname, Integer... ids) {
		int rows=0;
		try {
			rows=energyinfoMapper.deleteObjectById(dBname,ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int uptadeObject(String dBname, Energyinfo energyinfo) {
		int rows=0;
		try {
			rows=energyinfoMapper.updateObject(dBname,energyinfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}

	@Override
	public List<Energyinfo> findAllEnergyinfo(String dBname) {
		// TODO Auto-generated method stub
		return energyinfoMapper.findAllEnergyinfo(dBname);
	}

	@Override
	public boolean findCheckApptotal(String dBname) {
		int count;
		try {
			count = energyinfoMapper.findApptotalCount(dBname);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("重新尝试");
		}
		return count==0?false:true ;
	}

	@Override
	public Energyinfo findEnergyinfoByid(String dBname, Integer id) {
		// TODO Auto-generated method stub
		return energyinfoMapper.findEnergyinfoByid(dBname,id);
	}
}
