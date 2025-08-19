package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.mapper.QueryresultMapper;
import com.photonstudio.mapper.QueryresultinfoMapper;
import com.photonstudio.pojo.Queryresult;
import com.photonstudio.service.QueryresultService;
@Service
public class QueryresultServiceImpl implements QueryresultService {
	@Autowired
	private QueryresultMapper queryresultMapper;
	@Autowired
	private QueryresultinfoMapper QueryresultinfoMapper;

	@Override
	public List<Queryresult> findObject(String dBname) {
		// TODO Auto-generated method stub
		List<Queryresult>list =new ArrayList<>();
		try {
			list=queryresultMapper.findObject(dBname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return list;
	}

	@Override
	public int saveObject(String dBname, Queryresult queryresult) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=queryresultMapper.insertObject(dBname, queryresult);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	@Override
	@Transactional
	public int deleteObjectByid(String dBname, Integer id) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=QueryresultinfoMapper.deleteObjectByResultId(dBname, id);
			rows=queryresultMapper.deleteObjectById(dBname, id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int updateObject(String dBname, Queryresult queryresult) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=queryresultMapper.updateObject(dBname, queryresult);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}
	
}
