package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.QueryconditioninfoMapper;
import com.photonstudio.pojo.Queryconditioninfo;
import com.photonstudio.service.QueryconditioninfoService;
@Service
public class QueryconditioninfoServiceImpl implements QueryconditioninfoService {
	@Autowired
	private QueryconditioninfoMapper queryconditioninfoMapper;

	@Override
	public PageObject<Queryconditioninfo> findObject(String dBname, Integer conditionId, Integer pageCurrent,
			Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		List<Queryconditioninfo>list=new ArrayList<>();
		int rowCount= queryconditioninfoMapper.getRowCount(dBname,conditionId);
		int startIndex=(pageCurrent-1)*pageSize;
		list=queryconditioninfoMapper.findObject(dBname, conditionId,startIndex, pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public int deleteObjectByid(String dBname, Integer id) {
		// TODO Auto-generated method stub
		
		int rows=0;
		try {
			rows=queryconditioninfoMapper.deleteObjectById(dBname, id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int updateObject(String dBname, Queryconditioninfo queryconditioninfo) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=queryconditioninfoMapper.updateObject(dBname, queryconditioninfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}

	@Override
	public int saveObject(String dBname, Queryconditioninfo queryconditioninfo) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=queryconditioninfoMapper.insertObject(dBname, queryconditioninfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}
	
	
}
