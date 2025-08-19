package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.QueryresultinfoMapper;
import com.photonstudio.pojo.Queryresultinfo;
import com.photonstudio.service.QueryresultinfoService;
@Service
public class QueryresultinfoServiceImpl implements QueryresultinfoService {
	@Autowired
	private QueryresultinfoMapper queryresultinfoMapper;

	@Override
	public PageObject<Queryresultinfo> findObject(String dBname, Integer resultId, Integer pageCurrent,
			Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		List<Queryresultinfo>list=new ArrayList<>();
		int rowCount= queryresultinfoMapper.getRowCount(dBname,resultId);
		int startIndex=(pageCurrent-1)*pageSize;
		list=queryresultinfoMapper.findObject(dBname, resultId,startIndex, pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public int saveObject(String dBname, Queryresultinfo queryresultinfo) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=queryresultinfoMapper.insertObject(dBname, queryresultinfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	@Override
	public int deleteObjectByid(String dBname, Integer id) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=queryresultinfoMapper.deleteObjectById(dBname, id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int updateObject(String dBname, Queryresultinfo queryresultinfo) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=queryresultinfoMapper.updateObject(dBname, queryresultinfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}

}
