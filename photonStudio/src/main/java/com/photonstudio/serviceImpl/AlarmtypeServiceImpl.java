package com.photonstudio.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photonstudio.common.PagesUtil;
import com.photonstudio.common.exception.ServiceException;
import com.photonstudio.common.vo.PageObject;
import com.photonstudio.mapper.AlarmtypeMapper;
import com.photonstudio.pojo.Alarmtype;
import com.photonstudio.pojo.UserAlarmlevel;
import com.photonstudio.service.AlarmtypeService;
@Service
public class AlarmtypeServiceImpl implements AlarmtypeService{
	@Autowired
	private AlarmtypeMapper alarmtypeMapper;

	@Override
	public List<Alarmtype> findAll(String dBname) {
		
		return alarmtypeMapper.findAll(dBname);
	}

	@Override
	public int saveObject(String dBname, Alarmtype alarmtype) {
		int rows=0;
		try {
			rows=alarmtypeMapper.insertObject(dBname,alarmtype);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	@Override
	public int deleteObject(String dBname, Integer... ids) {
		if(ids==null||ids.length==0)throw new ServiceException("请选择");
		int rows=0;
		try {
			rows=alarmtypeMapper.deleteObjectById(dBname,ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

	@Override
	public int updateObject(String dBname, Alarmtype alarmtype) {
		int rows=0;
		try {
			rows=alarmtypeMapper.updateObject(dBname,alarmtype);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("更新失败");
		}
		return rows;
	}

	@Override
	public PageObject<Alarmtype> findObject(String dBname, Integer pageCurrent, Integer pageSize) {
		if (pageCurrent==null||pageCurrent<1) pageCurrent=1;
		if(pageSize==null||pageSize<5)pageSize=5;
		List<Alarmtype>list=new ArrayList<>();
		int startIndex=(pageCurrent-1)*pageSize;
		int rowCount=alarmtypeMapper.getRowCount(dBname);
		list=alarmtypeMapper.findObject(dBname,startIndex,pageSize);
		return PagesUtil.getPageObject(list, rowCount, pageSize, pageCurrent);
	}

	@Override
	public int updateUserAlarm(String dBname, Integer userid, Integer[] ids) {
		// TODO Auto-generated method stub
		int rows=0;
		int cnt = alarmtypeMapper.findcntByUserid(dBname,userid);
		if(cnt > 0)
		{
			alarmtypeMapper.deleteByUserid(dBname,userid);
		}
		for(Integer alarmlevel: ids)
		{
			alarmtypeMapper.insertUserAlarm(dBname,userid,alarmlevel);
			rows++;
		}
		return rows;
	}

	@Override
	public List<UserAlarmlevel> findUserAlarm(String dBname, Integer userid) {
		// TODO Auto-generated method stub
		return alarmtypeMapper.findUserAlarm(dBname,userid);
	}
}
