package com.photonstudio.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.QsActivelogMapper;
import com.photonstudio.pojo.QsActivelog;
import com.photonstudio.service.QsActivelogService;
@Service
public class QsActivelogServiceImpl implements QsActivelogService {
	@Autowired
	private QsActivelogMapper qsActivelogMapper;
	@Override
	public PageObject<QsActivelog> findObject(String dBname, String username, Integer pageCurrent, Integer pageSize,
			Date startTime, Date endTime) {
		if(pageCurrent==null||pageCurrent<1)pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		int rowCount=qsActivelogMapper.getRowCount(dBname,username,startTime,endTime);
		int startIndex=(pageCurrent-1)*pageSize;
		List<QsActivelog>list=qsActivelogMapper.findObject(dBname,username,
				startIndex,pageSize,startTime,endTime);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public int saveObject(String dBname, QsActivelog qsActivelog) {
		int rows=0;
		try {
			rows=qsActivelogMapper.insertObject(dBname,qsActivelog);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	@Override
	public int deleteObject(String dBname, Integer... ids) {
		int rows=0;
		try {
			rows=qsActivelogMapper.deleteObjectById(dBname,ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int updateObject(String dBname, QsActivelog qsActivelog) {
		int rows=0;
		try {
			rows=qsActivelogMapper.updateObject(dBname,qsActivelog);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}
	
}
