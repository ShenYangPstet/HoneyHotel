package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.mapper.QueryconditionMapper;
import com.photonstudio.mapper.QueryconditioninfoMapper;
import com.photonstudio.pojo.Querycondition;
import com.photonstudio.service.QueryconditionService;
@Service
public class QueryconditionServiceImpl implements QueryconditionService {
	@Autowired
	private QueryconditionMapper queryconditionMapper;
	@Autowired
	private QueryconditioninfoMapper queryconditioninfoMapper;

	@Override
	public List<Querycondition> findObject(String dBname) {
		// TODO Auto-generated method stub
		List<Querycondition>list =new ArrayList<>();
		try {
			list=queryconditionMapper.findObject(dBname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("查询失败");
		}
		return list;
	}

	@Override
	public int saveObject(String dBname, Querycondition querycondition) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=queryconditionMapper.insertObject(dBname, querycondition);
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
			rows=queryconditioninfoMapper.deleteObjectByConditionId(dBname, id);
			rows=queryconditionMapper.deleteObjectById(dBname, id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int updateObject(String dBname, Querycondition querycondition) {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			rows=queryconditionMapper.updateObject(dBname, querycondition);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}
	
}
