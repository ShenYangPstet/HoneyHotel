package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.DlldrinfoMapper;
import com.photonstudio.pojo.Dlldrinfo;
import com.photonstudio.pojo.Drllinfo;
import com.photonstudio.pojo.Drlltype;
import com.photonstudio.service.DlldrinfoService;
@Service
public class DlldrinfoServiceImpl implements DlldrinfoService{
	@Autowired
	private DlldrinfoMapper dlldrinfoMapper;

	@Override
	public PageObject<Dlldrinfo> findObject(String dBname, String drname, Integer pageCurrent, Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		List<Dlldrinfo>list=new ArrayList<>();
		int rowCount=dlldrinfoMapper.getRowCount(dBname, drname);
		int startIndex=(pageCurrent-1)*pageSize;
		list=dlldrinfoMapper.findObject(dBname, drname, startIndex, pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public int saveObject(String dBname, Dlldrinfo dlldrinfo) {
		// TODO Auto-generated method stub
		int row=0;
		try {
			row=dlldrinfoMapper.insertObject(dBname, dlldrinfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return row;
	}

	@Override
	public int deleteObjectById(String dBname, Integer[] ids) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=dlldrinfoMapper.deleteObjectById(dBname, ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int updateObject(String dBname, Dlldrinfo dlldrinfo) {
		// TODO Auto-generated method stub
		int row=0;
		try {
			row=dlldrinfoMapper.updateObject(dBname, dlldrinfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return row;
	}

	@Override
	public List<Dlldrinfo> findDlldrinfo(String dBname, String drname) {
		// TODO Auto-generated method stub
		List<Dlldrinfo>list=dlldrinfoMapper.findObject(dBname, drname, null, null);
		return list;
	}

	@Override
	public void importObjects(String dBname, List<Dlldrinfo> list) {
		// TODO Auto-generated method stub
		int rows=0;
		Integer drid;
		for (Dlldrinfo dlldrinfo : list) {
			drid=dlldrinfo.getDrid();
			if(drid==null||drid==0) {
				dlldrinfoMapper.insertObject(dBname, dlldrinfo);
			}else {
				rows=dlldrinfoMapper.findObjectById(dBname,drid);
				if(rows==1) {
					dlldrinfoMapper.updateObject(dBname, dlldrinfo);
				}else {
					dlldrinfoMapper.insertObject(dBname, dlldrinfo);
				}
			}
		}
		
	}

	@Override
	public List<Drllinfo> findDrllinfo(String dBname) {
		// TODO Auto-generated method stub
		List<Drllinfo>list=dlldrinfoMapper.findDrllinfo(dBname);
		return list;
	}

	@Override
	public List<Drlltype> findDrlltypeBydrid(String dBname, Integer drid) {
		// TODO Auto-generated method stub
		List<Drlltype>list=dlldrinfoMapper.findDrlltypeBydrid(dBname,drid);
		return list;
	}
}
