package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.QsLdLogMapper;
import com.photonstudio.pojo.QsLdLog;
import com.photonstudio.service.QsLdLogService;
@Service
public class QsLdLogServiceImp implements QsLdLogService{
	@Autowired
	private QsLdLogMapper qsLdLogMapper;

	@Override
	public PageObject<QsLdLog> findObject(String dBname, Integer pageCurrent, Integer pageSize, Integer type,
			Integer state, Date startTime, Date endTime) {
		if(pageCurrent==null||pageCurrent<1)pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		List<QsLdLog>list=new ArrayList<>();
		int rowCount=qsLdLogMapper.getRowCount(dBname,type,state,startTime,endTime);
		int startIndex=(pageCurrent-1)*pageSize;
		list=qsLdLogMapper.findObject(dBname,type,state,startIndex,pageSize,startTime,endTime);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public int saveObject(String dBname, QsLdLog qsLdLog) {
		int rows=0;
		try {
			rows=qsLdLogMapper.insertObject(dBname,qsLdLog);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	@Override
	public int deleteObjectById(String dBname, Integer[] ids) {
		int rows=0;
		try {
			rows=qsLdLogMapper.deleteObjectById(dBname,ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int updateObject(String dBname, QsLdLog qsLdLog) {
		int rows=0;
		try {
			rows=qsLdLogMapper.updateObject(dBname,qsLdLog);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}

	@Override
	public List<QsLdLog> findAll(String dBname, Date startTime, Date endTime) {
		List<QsLdLog>list=new ArrayList<>();
		try {
			list=qsLdLogMapper.findAllByTime(dBname, startTime, endTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
